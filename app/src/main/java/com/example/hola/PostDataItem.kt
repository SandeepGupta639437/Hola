package com.example.hola

data class PostDataItem(
    val comments_count: Int,
    val content: String,
    val created_at: String,
    val created_by: Int,
    val id: Int,
    val is_public: Boolean,
    val likes_count: Int,
    val media: Any,
    val tags: String,
    val updated_at: String
)