package com.example.trustline.navigation

enum class Routes {
    LOGIN, REGISTER, FORGOT_PASSWORD, RESET_PASSWORD
}

sealed class ScreenConfiguration(val route: String) {
    sealed class UnauthenticatedScreen(unauthenticatedRoute: String) :
        ScreenConfiguration(unauthenticatedRoute) {
        data object Register : UnauthenticatedScreen(Routes.REGISTER.name)
        data object Login : UnauthenticatedScreen(Routes.LOGIN.name)
        data object ForgotPassword : UnauthenticatedScreen(Routes.FORGOT_PASSWORD.name)
        data object ResetPassword : UnauthenticatedScreen(Routes.RESET_PASSWORD.name)
    }
}