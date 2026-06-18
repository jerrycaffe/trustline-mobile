package com.example.trustline.data

import okhttp3.ResponseBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface ApiService {
    @POST
    suspend fun post(
        @Url url: String,
        @Body requestBody: Any
    ): Response<ResponseBody>

    @GET
    suspend fun get(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>

    @GET
    suspend fun getWithoutAuth(
        @Url url: String
    ): Response<ResponseBody>

    @Multipart
    @POST("/api/v1/cases")
    suspend fun submitCase(
        @Header("Authorization") authorization: String,
        @Part("case") caseBody: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Response<ResponseBody>

    @GET("/api/v1/cases/my-cases")
    suspend fun getMyCases(
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>
}