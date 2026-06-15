package com.example.trustline.data

import com.example.trustline.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

interface AppContainer {
    val authRepository: AuthRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = BuildConfig.BASE_URL

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    //This is retrofit settings/configuration
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(baseUrl)
        .build()

    //This is the actual api call with retrofit
    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    private val apiServiceImpl: ApiServiceImpl by lazy {
        ApiServiceImpl(apiService, gson)
    }

    //    This is what i want to expose to the outside word
    override val authRepository: AuthRepositoryImpl by lazy {
        AuthRepositoryImpl(apiServiceImpl)
    }
}