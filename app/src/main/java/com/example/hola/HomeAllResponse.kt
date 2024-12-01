package com.example.hola

import com.google.gson.annotations.SerializedName

data class HomeAllResponse(
    @SerializedName("comments_count") val comments_count: Int,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("created_by") val created_by: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("is_public") val is_public: Boolean,
    @SerializedName("likes_count") val likes_count: Int,
    @SerializedName("media") val media: String,
    @SerializedName("tags") val tags: String,
    @SerializedName("updated_at") val updated_at: String
)
