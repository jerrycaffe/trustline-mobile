package com.example.trustline.data

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @POST
    suspend fun post(
        @Url url: String,
        @Body requestBody: Any
    ): Response<ResponseBody>
}