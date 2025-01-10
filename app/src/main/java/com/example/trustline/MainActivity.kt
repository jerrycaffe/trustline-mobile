package com.example.trustline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.trustline.presentation.auth.login.presentation.LoginScreen
import com.example.trustline.presentation.welcome.WelcomeView
import com.example.trustline.ui.theme.TrustlineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrustlineTheme {
                Scaffold { innerPadding ->

                    LoginScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrustlineTheme {
        WelcomeView()
    }
}
