package com.example.hola

data class UserProfileApi(
    val background_photo: Any,
    val bio: String,
    val id: Int,
    val num_followers: Int,
    val num_following: Int,
    val num_posts: Int,
    val profile_photo: Any,
    val username: String
)