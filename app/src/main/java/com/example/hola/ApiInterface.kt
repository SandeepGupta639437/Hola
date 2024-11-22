package com.example.hola

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("product")
    fun getApiData(): Call<ApiData>
}