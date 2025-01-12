package com.example.trustline.navigation

enum class Routes {
    LOGIN, REGISTER
}

sealed class ScreenConfiguration(val route: String) {
    sealed class UnauthenticatedScreen(unauthenticatedRoute: String) :
        ScreenConfiguration(unauthenticatedRoute) {
        data object Register : UnauthenticatedScreen(Routes.REGISTER.name)
        data object Login : UnauthenticatedScreen(Routes.LOGIN.name)
    }
}