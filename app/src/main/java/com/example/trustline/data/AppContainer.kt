package com.example.trustline.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val authRepository: AuthRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "http://localhost:8181/"

    //This is retrofit settings/configuration
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    //This is the actual api call with retrofit
    private val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    //    This is what i want to expose to the outside word
    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authService)
    }
}