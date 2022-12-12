package com.example.dpsv2.network

import com.android.volley.Response
import com.example.dpsv2.models.ApiResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {
    @GET("/")
    suspend fun getAllUsers(): Call<ApiResponse>

    @GET("/student/{email}")
    suspend fun getStudentByEmail(@Path("email") email: String): Response<ApiResponse>

    @PUT("/student/{email}")
    suspend fun editStudentBySUID(@Path("email") email: String, @Body requestBody: RequestBody): Call<ApiResponse>


}