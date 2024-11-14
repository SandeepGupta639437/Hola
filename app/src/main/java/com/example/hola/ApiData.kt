package com.example.hola

data class ApiData(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)