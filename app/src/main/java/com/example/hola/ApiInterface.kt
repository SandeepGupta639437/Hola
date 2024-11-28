package com.example.hola

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {
    @GET("api/accounts/homepage/")
    fun getHomeData(): Call<HomePost>
    @GET("api/accounts/profile/")
    fun getUserProfileData(): Call<UserProfileApi>
    @GET("products")
    fun getApiData(): Call<ApiData>
}