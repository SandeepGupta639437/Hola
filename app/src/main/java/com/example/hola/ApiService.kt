package com.example.hola

import Backend.SignUpRequest
import Backend.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import Backend.LoginResponse
import Backend.LoginRequest
import Backend.*

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.Part


interface ApiService {
    @POST("/api/auth/login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>


    @POST("api/auth/register/")
    suspend fun registerUser(@Body signup: SignUpRequest): Response<SignUpResponse>


    @POST("api/auth/forgot-password/")
    suspend fun forgotPassword(@Body requestBody: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @POST("api/auth/reset-password/")
    suspend fun resetPassword(@Body requestBody: ResetPasswordRequest): Response<ResetPasswordResponse>


    @Multipart
    @POST("/api/posts/create/")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Part("content") content: RequestBody,
        @Part("tags") tags: RequestBody,
        @Part("isPublic") isPublic: RequestBody,
        @Part media: MultipartBody.Part?
    ): Response<CreatePostResponse>




}



