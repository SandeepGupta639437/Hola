package Backend

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class CreatePostRequest(
    val content: RequestBody,
    val isPublic: RequestBody,
    val tags: RequestBody? = null,
    val media: MultipartBody.Part? = null
)