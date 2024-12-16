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
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType

import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Modifier.isPublic



class createPostActivity : AppCompatActivity() {

    private var mediaUri: Uri? = null
    private lateinit var selectedMediaCard: CardView
    private lateinit var selectedImageView: ImageView
    private lateinit var selectedVideoView: VideoView
    private lateinit var etContent: EditText
    private lateinit var etTags: EditText
    private lateinit var btnPost: Button
    private lateinit var btnAddMedia: Button
    private lateinit var switchVisibility: CheckBox

    private val PICK_MEDIA_REQUEST = 1000

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

        btnAddMedia.setOnClickListener { openMediaPicker() }

        btnPost.setOnClickListener { handlePostCreation() }

        findViewById<ImageView>(R.id.btnClose).setOnClickListener {
            val intent = Intent(this@createPostActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openMediaPicker() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/* video/*"
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

    private fun showSelectedMedia(uri: Uri) {
        val mimeType = contentResolver.getType(uri) ?: ""
        when {
            mimeType.startsWith("image") -> {
                selectedImageView.setImageURI(uri)
                selectedImageView.visibility = View.VISIBLE
                selectedVideoView.visibility = View.GONE
            }
            mimeType.startsWith("video") -> {
                selectedVideoView.setVideoURI(uri)
                selectedVideoView.visibility = View.VISIBLE
                selectedImageView.visibility = View.GONE
            }
        }
        selectedMediaCard.visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvMediaStatus).text = "Media Selected"
    }

    private fun handlePostCreation() {
        val content = etContent.text.toString()
        val tags = etTags.text.toString()
        val isPublic = switchVisibility.isChecked

        if (content.isBlank()) {
            Toast.makeText(this, "Content cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", "") ?: ""

        if (accessToken.isEmpty()) {
            Toast.makeText(this, "Token is missing or expired.", Toast.LENGTH_SHORT).show()
            return
        }

        val contentRequestBody = RequestBody.create("text/plain".toMediaType(), content)
        val tagsRequestBody = RequestBody.create("text/plain".toMediaType(), tags)
        val isPublicRequestBody = RequestBody.create("text/plain".toMediaType(), isPublic.toString())

        val mediaPart = mediaUri?.let { uri ->
            val file = getFileFromUri(uri) // Replace with your function to get a File from the URI
            file?.let {
                val requestFile = RequestBody.create(
                    contentResolver.getType(uri)?.toMediaTypeOrNull() ?: "application/octet-stream".toMediaTypeOrNull(),
                    it
                )
                MultipartBody.Part.createFormData("media", file.name, requestFile)
            }
        }



        uploadPost(accessToken, content, isPublic, tags, mediaPart)
}

    private fun getFileFromUri(uri: Uri): File? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val file = File(cacheDir, "uploaded_file")
            val outputStream = FileOutputStream(file)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun uploadPost(
        accessToken: String,
        content: String,
        isPublic: Boolean,
        tags: String,
        media: MultipartBody.Part?
    ) {
        val apiService = RetrofitInstance.api

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.createPost(
                    "Bearer $accessToken",
                    content,
                    isPublic,
                    tags,
                    media
                )

                if (response.isSuccessful) {
                    Toast.makeText(this@createPostActivity, "Post uploaded successfully!", Toast.LENGTH_SHORT).show()
                    Log.d("CreatePostActivity", "Response: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@createPostActivity, "Error: $errorBody", Toast.LENGTH_SHORT).show()
                    Log.e("CreatePostActivity", "Error response: $errorBody")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@createPostActivity, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



