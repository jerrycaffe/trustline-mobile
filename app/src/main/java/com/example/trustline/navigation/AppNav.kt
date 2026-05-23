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
import com.example.trustline.presentation.dashboard.presentation.HomeScreen
import com.example.trustline.presentation.report.presentation.presentation.ReportCaseInfoScreen
import com.example.trustline.presentation.seekHelp.presentation.SeekHelpCaseInfoScreen
import com.example.trustline.presentation.splash.SplashScreen
import com.example.trustline.presentation.trackCase.presentation.TrackCaseInfoScreen

@Composable
fun AppNavigation(
    navController: NavHostController, globalViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = ScreenConfiguration.UnauthenticatedScreen.Splash.route
    ) {
        composable(ScreenConfiguration.UnauthenticatedScreen.Splash.route) {
            SplashScreen(navController)
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.Register.route) {
            SignupScreen(navController, globalViewModel)
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.Login.route) {
            LoginScreen(navController, globalViewModel)
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.ForgotPassword.route) {
            ForgotPasswordScreen(
                navController, globalViewModel
            )
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.ResetPassword.route) {
            ResetPasswordScreen(
                navController, globalViewModel
            )
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.OtpValidation.route) {
            OtpVerificationScreen(navController, globalViewModel)
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.Home.route) {
            HomeScreen(navController, globalViewModel)
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.ReportCaseInfo.route) {
            ReportCaseInfoScreen(
                onBackClicked = { navController.popBackStack() },
                onContinueClicked = { /* TODO: Next screen */ }
            )
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.TrackCaseInfo.route) {
            TrackCaseInfoScreen(
                onBackClicked = { navController.popBackStack() },
                onContinueClicked = { /* TODO: Next screen */ }
            )
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.SeekHelpInfo.route) {
            SeekHelpCaseInfoScreen(
                onBackClicked = { navController.popBackStack() },
                onContinueClicked = { /* TODO: Next screen */ }
            )
        }

    }
}