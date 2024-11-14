package com.example.hola

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("products")
    fun getApiData(): Call<ApiData>
}