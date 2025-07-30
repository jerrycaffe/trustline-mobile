package com.example.trustline.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//This is purely to define the endpoints to be called in the
// repository but annotated with retrofit annotations
//it will perform the actual call to the desired endpoint
//The Response is an http response that contains value like isSuccessful
interface AuthService {
    @POST("/api/v1/auth/register")
    suspend fun registerUser(@Body request: RegisterUserRequest): Response<RegisterUserRes>
}