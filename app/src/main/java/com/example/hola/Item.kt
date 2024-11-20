package com.example.hola

import Backend.Request

data class Item(
    val name: String,
    val request: Request,
    val response: List<Any>
)