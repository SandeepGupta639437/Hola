package com.example.hola

import Backend.CreatePostResponse
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    private lateinit var switchVisibility: Switch


    private val PICK_MEDIA_REQUEST = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        // Initialize UI components
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
            openMediaPicker() // Open the file picker to select media
        }

        // Handle the Post button click
        btnPost.setOnClickListener {
            val mediaPart = createMediaPart(mediaUri, this)
            val content = etContent.text.toString()
            val tags = etTags.text.toString()
            val isPublic = switchVisibility.isChecked

            // Perform API call to upload the post with media and content
            if (mediaPart != null) {
                uploadPost(mediaPart, content, tags,isPublic)
            } else {
                Log.e("CreatePostActivity", "No media selected")
            }
        }
    }

    // Open media picker (image/video)
    private fun openMediaPicker() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/* video/*" // Allow image and video selection
        }
        startActivityForResult(intent, PICK_MEDIA_REQUEST)
    }

    // Handle the result of the media picker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == PICK_MEDIA_REQUEST) {
            data?.data?.let { uri ->
                mediaUri = uri // Save the selected media URI
                showSelectedMedia(uri) // Show the selected media (image or video) in the UI
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

    // Simulate the upload of the post (you would call your API here)
     fun uploadPost(mediaPart: MultipartBody.Part, content: String, tags: String,isPublic: Boolean) {
        // Make the API call to upload the post
        // You would use Retrofit or a similar library to handle this network request


        // Sample upload
        val contentPart = RequestBody.create("text/plain".toMediaTypeOrNull(), content)
        val tagsPart = RequestBody.create("text/plain".toMediaTypeOrNull(), tags)
        val isPublicPart = isPublic.toString().toRequestBody("text/plain".toMediaTypeOrNull())


        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", "") ?: ""

        if (accessToken.isEmpty()) {
            Toast.makeText(this, "Token is missing or expired.", Toast.LENGTH_SHORT).show()
            return
        }

        // Make the API call to upload the post
        val apiService = RetrofitInstance.api

        CoroutineScope(Dispatchers.Main).launch {

            try {


                val response = apiService.createPost(
                    "Bearer $accessToken",
                    contentPart,
                    tagsPart,
                    isPublicPart,
                    mediaPart
                )


                if (response.isSuccessful) {
                    // Handle successful response
                    Log.d("CreatePostActivity", "Post uploaded successfully!, ${mediaPart}")
                    Toast.makeText(this@createPostActivity, "Post successFull!!", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle error response
                    Log.e("CreatePostActivity", "Error uploading post: ${response.message()}")

                        Toast.makeText(
                            this@createPostActivity,
                            "Error uploading post: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()

                }
            } catch (e: Exception) {
                Toast.makeText(this@createPostActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    }
}
