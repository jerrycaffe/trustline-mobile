package com.example.trustline.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trustline.MainViewModel
import com.example.trustline.presentation.auth.forgot_password.presentation.ForgotPasswordScreen
import com.example.trustline.presentation.auth.login.presentation.LoginScreen
import com.example.trustline.presentation.auth.otp.presentation.OtpVerificationScreen
import com.example.trustline.presentation.auth.register.presentation.SignupScreen
import com.example.trustline.presentation.auth.reset_password.presentation.ResetPasswordScreen

@Composable
fun AppNavigation(
    navController: NavHostController, globalViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = ScreenConfiguration.UnauthenticatedScreen.Register.route
    ) {
        composable(ScreenConfiguration.UnauthenticatedScreen.Register.route) {
            SignupScreen(navController, globalViewModel)
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.Login.route) {
            LoginScreen(navController)
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.ForgotPassword.route) {
            ForgotPasswordScreen(
                navController
            )
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.ResetPassword.route) {
            ResetPasswordScreen(
                navController
            )
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.OtpValidation.route) {
            OtpVerificationScreen(navController, globalViewModel)
        }

    }
}