package com.example.hola

data class ImageUnsplash(
    val id: String,
    val urls: Urls
)

data class Urls(
    val small: String, // URL for the small-sized image
    val regular: String // URL for the regular-sized image
)
