package com.example.hola

import Backend.CreatePostRequest
import Backend.CreatePostResponse
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.hola.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody

import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Modifier.isPublic


class createPostActivity : AppCompatActivity() {

    private lateinit var mediaUri: Uri
    private lateinit var selectedMediaCard: CardView
    private lateinit var selectedImageView: ImageView
    private lateinit var selectedVideoView: VideoView
    private lateinit var etContent: EditText
    private lateinit var etTags: EditText
    private lateinit var btnPost: Button
    private lateinit var btnAddMedia: Button
    private lateinit var switchVisibility: CheckBox
var isChecked=false;


    private val PICK_MEDIA_REQUEST = 1000

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        selectedMediaCard = findViewById(R.id.selectedMedia)
        selectedImageView = findViewById(R.id.selectedImageView)
        selectedVideoView = findViewById(R.id.selectedVideoView)
        etContent = findViewById(R.id.etContent)
        etTags = findViewById(R.id.etTags)
        btnPost = findViewById(R.id.btnPost)
        btnAddMedia = findViewById(R.id.btnAddMedia)

        switchVisibility = findViewById(R.id.switchVisibility)

        // Handle the Add Media button click
        btnAddMedia.setOnClickListener {
            openMediaPicker()
        }

        btnPost.setOnClickListener {
            val content = etContent.text.toString()
            val tags = etTags.text.toString()
            val isPublic = switchVisibility.isChecked


            val mediaUrl = mediaUri.toString()

//            if (mediaUrl.isNotEmpty()) {
//                uploadPost(content, tags, isPublic, mediaUrl)
//                Log.d("${isPublic}","ispublic ${isPublic}")
//            } else {
//                uploadPost(content, tags, isPublic, null)
//            }


            uploadPost(content, tags, isPublic, mediaUrl)
        }
        findViewById<ImageView>(R.id.btnClose).setOnClickListener{

            val intent = Intent(this@createPostActivity, MainActivity::class.java)
            startActivity(intent)

          }





    }

    private fun openMediaPicker() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/* video/*" // Allow image and video selection
        }
        startActivityForResult(intent, PICK_MEDIA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == PICK_MEDIA_REQUEST) {
            data?.data?.let { uri ->
                mediaUri = uri
                showSelectedMedia(uri)
            }
        }
    }

    // This method shows selected media in the UI (image or video)
    private fun showSelectedMedia(uri: Uri) {
        val mimeType = contentResolver.getType(uri) ?: ""
        when {
            mimeType.startsWith("image") -> {
                selectedImageView.setImageURI(uri)
                selectedImageView.visibility = ImageView.VISIBLE
                selectedVideoView.visibility = VideoView.GONE
            }

            mimeType.startsWith("video") -> {
                selectedVideoView.setVideoURI(uri)
                selectedVideoView.visibility = VideoView.VISIBLE
                selectedImageView.visibility = ImageView.GONE
            }
        }
        selectedMediaCard.visibility = CardView.VISIBLE

        findViewById<TextView>(R.id.tvMediaStatus).text="Media Selected"
    }

    // Convert Uri to File
    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.cacheDir, "uploaded_file")
            val outputStream = FileOutputStream(file)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            file
        } catch (e: Exception) {
            null
        }
    }

    // Create Multipart for media upload
    private fun createMediaPart(mediaUri: Uri?, context: Context): MultipartBody.Part? {
        return mediaUri?.let {
            val mediaFile = getFileFromUri(context, it)
            mediaFile?.let { file ->
                val mimeType = context.contentResolver.getType(it) ?: "application/octet-stream"
                val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                MultipartBody.Part.createFormData("media", file.name, requestFile)
            }
        }
    }

    fun uploadPost(content: String, tags: String, isPublic: Boolean, mediaUrl: String?) {
        val contentPart = content.toRequestBody("text/plain".toMediaTypeOrNull())
        val tagsPart = tags.toRequestBody("text/plain".toMediaTypeOrNull())
        val isPublicPart = isPublic.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", "") ?: ""

        if (accessToken.isEmpty()) {
            Toast.makeText(this, "Token is missing or expired.", Toast.LENGTH_SHORT).show()
            return
        }

        val createPostRequest = CreatePostRequest(
            content = contentPart.toString(),
            tags = tagsPart.toString(),
            isPublic = isPublic,
            mediaUrl = mediaUrl.toString()
        )

        val apiService = RetrofitInstance.api

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.createPost(
                    "Bearer $accessToken",
                    createPostRequest // Pass the CreatePostRequest directly
                )

                if (response.isSuccessful) {
                    // Handle successful response
                    Log.d("CreatePostActivity", "Post uploaded successfully! ${mediaUrl}")
                    Toast.makeText(this@createPostActivity, "Post uploaded successfully! ${mediaUrl}", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle error response
                    Log.e("CreatePostActivity", "Error uploading post: ${response.message()}")
                    Toast.makeText(this@createPostActivity, "Error uploading post: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@createPostActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

