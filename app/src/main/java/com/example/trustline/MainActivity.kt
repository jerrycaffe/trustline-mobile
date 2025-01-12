package com.example.trustline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trustline.navigation.AppNavigation
import com.example.trustline.presentation.welcome.WelcomeView
import com.example.trustline.ui.theme.TrustlineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrustlineTheme {
                Scaffold { innerPadding ->
                    val controller: NavHostController = rememberNavController()
                    val viewModel = MainViewModel()
                    AppNavigation(navController = controller, viewModel = viewModel)
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
