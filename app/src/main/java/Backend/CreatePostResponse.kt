package Backend

data class CreatePostResponse(
    val id: Int,
    val created_by: Int,
    val content: String,
    val media: String?,
    val created_at: String,
    val updated_at: String,
    val is_public: Boolean,
    val likes_count: Int,
    val comments_count: Int,
    val tags: String?
)