package com.example.trustline.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trustline.MainViewModel
import com.example.trustline.presentation.auth.login.presentation.LoginScreen
import com.example.trustline.presentation.auth.register.presentation.SignupScreen

@Composable
fun AppNavigation(navController: NavController, viewModel: MainViewModel) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = ScreenConfiguration.UnauthenticatedScreen.Register.route
    ) {
        composable(ScreenConfiguration.UnauthenticatedScreen.Register.route) {
            SignupScreen()
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.Login.route) {
            LoginScreen()
        }

    }
}