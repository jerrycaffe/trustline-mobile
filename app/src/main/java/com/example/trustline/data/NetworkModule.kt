package com.example.trustline.data
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    @Provides
//    fun provideBaseUrl() = "http://localhost:8181/"
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(baseUrl: String): Retrofit =
//        Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//    @Provides
//    @Singleton
//    fun provideApi(retrofit: Retrofit): TrustlineApi =
//        retrofit.create(TrustlineApi::class.java)
//}
