package com.example.trustline

import android.app.Application
import com.example.trustline.data.AppContainer
import com.example.trustline.data.DefaultAppContainer


class TrustlineApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
