package com.example.trustline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trustline.navigation.AppNavigation
import com.example.trustline.ui.theme.TrustlineTheme


class MainActivity : ComponentActivity() {
    private val globalViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TrustlineTheme {
                Scaffold { _ ->
                    val controller: NavHostController = rememberNavController()
                    AppNavigation(navController = controller, globalViewModel)
                }
            }
        }
    }
}
