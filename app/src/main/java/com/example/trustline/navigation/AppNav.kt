package com.example.trustline.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trustline.MainViewModel
import com.example.trustline.presentation.auth.LoginAware.presentation.LoginAwareScreen
import com.example.trustline.presentation.auth.forgot_password.presentation.ForgotPasswordScreen
import com.example.trustline.presentation.auth.login.presentation.LoginScreen
import com.example.trustline.presentation.auth.otp.presentation.OtpVerificationScreen
import com.example.trustline.presentation.auth.register.presentation.SignupScreen
import com.example.trustline.presentation.auth.reset_password.presentation.ResetPasswordScreen
import com.example.trustline.presentation.common.MainTabWrapper
import com.example.trustline.presentation.dashboard.presentation.HomeScreen
import com.example.trustline.presentation.gbvResources.presentation.GbvResourceDetailsScreen
import com.example.trustline.presentation.gbvResources.presentation.GbvResourcesScreen
import com.example.trustline.presentation.report.presentation.presentation.AuthReportCaseScreen
import com.example.trustline.presentation.report.presentation.presentation.ReportCaseInfoScreen
import com.example.trustline.presentation.report.presentation.presentation.ReportScreen
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
            MainTabWrapper(navController, globalViewModel) {
                HomeScreen(navController, globalViewModel)
            }
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.ReportCaseInfo.route) {
            ReportCaseInfoScreen(
                onBackClicked = { navController.popBackStack() },
                onContinueClicked = {
                    globalViewModel.rememberPendingPostAuthRoute(Routes.AUTH_REPORT_CASE.name)
                    navController.navigate(Routes.LOGIN_AWARE.name)
                })
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.TrackCaseInfo.route) {
            TrackCaseInfoScreen(
                onBackClicked = { navController.popBackStack() },
                onContinueClicked = { navController.navigate(Routes.LOGIN_AWARE.name) })
        }
        composable(ScreenConfiguration.UnauthenticatedScreen.SeekHelpInfo.route) {
            SeekHelpCaseInfoScreen(
                onBackClicked = { navController.popBackStack() },
                onContinueClicked = { navController.navigate(Routes.LOGIN_AWARE.name) })
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.LoginAware.route) {
            LoginAwareScreen(
                onBackClicked = { navController.popBackStack() },
                onContinueClicked = { navController.navigate(Routes.LOGIN.name) })
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.Report.route) {
            ReportScreen(onBackClicked = { navController.popBackStack() })
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.AuthReportCase.route) {
            AuthReportCaseScreen(navController = navController)
        }

        // Bottom Navigation Destinations
        composable(ScreenConfiguration.UnauthenticatedScreen.Cases.route) {
            MainTabWrapper(navController, globalViewModel) {
                TrackCaseInfoScreen(
                    onBackClicked = { navController.popBackStack() },
                    onContinueClicked = { navController.navigate(Routes.LOGIN_AWARE.name) })
            }
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.Zen.route) {
            MainTabWrapper(navController, globalViewModel) {
                SeekHelpCaseInfoScreen(
                    onBackClicked = { navController.popBackStack() },
                    onContinueClicked = { navController.navigate(Routes.LOGIN_AWARE.name) })
            }
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.Chat.route) {
            MainTabWrapper(navController, globalViewModel) {
                HomeScreen(navController, globalViewModel)
            }
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.GbvResources.route) {
            GbvResourcesScreen(navController, globalViewModel)
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.GbvResourcesDetail.route) {
            GbvResourceDetailsScreen(navController::popBackStack, globalViewModel)
        }

        composable(ScreenConfiguration.UnauthenticatedScreen.Profile.route) {
            MainTabWrapper(navController, globalViewModel) {
                HomeScreen(navController, globalViewModel)
            }
        }

    }
}