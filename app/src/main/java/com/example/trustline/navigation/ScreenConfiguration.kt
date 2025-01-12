package com.example.trustline.navigation

enum class Routes {
    LOGIN, REGISTER
}

sealed class ScreenConfiguration(val route: String) {
    sealed class UnauthenticatedScreen(unauthenticatedRoute: String) :
        ScreenConfiguration(unauthenticatedRoute) {
        object Register : UnauthenticatedScreen(Routes.REGISTER.name)
        object Login : UnauthenticatedScreen(Routes.LOGIN.name)
    }
}