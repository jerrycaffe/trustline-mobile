package com.example.trustline.data

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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
}