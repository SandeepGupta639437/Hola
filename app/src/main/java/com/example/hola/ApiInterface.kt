package com.example.hola

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {
    interface HomePageApi {
        @GET("home")
        suspend fun getHomePageDetails(): Response<List<HomePageResponse>>
    }


    @GET("api/accounts/profile/")
    fun getUserProfileData(): Call<UserProfileApi>
    @GET("products")
    fun getApiData(): Call<ApiData>
}