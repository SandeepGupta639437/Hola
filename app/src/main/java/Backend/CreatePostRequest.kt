package Backend

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class CreatePostRequest(
    val content: String,
    val isPublic: Boolean,
    val tags: String,
    val mediaUrl: String? = null // String instead of MultipartBody.Part
)
