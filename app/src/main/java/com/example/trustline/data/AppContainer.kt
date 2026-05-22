package com.example.trustline.data

import com.example.trustline.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val authRepository: AuthRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = BuildConfig.BASE_URL

    //This is retrofit settings/configuration
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    //This is the actual api call with retrofit
    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    private val apiServiceImpl: ApiServiceImpl by lazy {
        ApiServiceImpl(apiService)
    }

    //    This is what i want to expose to the outside word
    override val authRepository: AuthRepositoryImpl by lazy {
        AuthRepositoryImpl(apiServiceImpl)
    }
}