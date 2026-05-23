package com.example.trustline.navigation

enum class Routes {
    SPLASH,
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
    RESET_PASSWORD,
    OTP_VALIDATION,
    HOME,
    REPORT_CASE_INFO,
    TRACK_CASE_INFO,
    SEEK_HELP_INFO
}

sealed class ScreenConfiguration(val route: String) {
    sealed class UnauthenticatedScreen(unauthenticatedRoute: String) :
        ScreenConfiguration(unauthenticatedRoute) {
        data object Splash : UnauthenticatedScreen(Routes.SPLASH.name)
        data object Register : UnauthenticatedScreen(Routes.REGISTER.name)
        data object Login : UnauthenticatedScreen(Routes.LOGIN.name)
        data object ForgotPassword : UnauthenticatedScreen(Routes.FORGOT_PASSWORD.name)
        data object ResetPassword : UnauthenticatedScreen(Routes.RESET_PASSWORD.name)
        data object OtpValidation : UnauthenticatedScreen(Routes.OTP_VALIDATION.name)
        data object Home : UnauthenticatedScreen(Routes.HOME.name)
        data object ReportCaseInfo : UnauthenticatedScreen(Routes.REPORT_CASE_INFO.name)

        data object TrackCaseInfo : UnauthenticatedScreen(Routes.TRACK_CASE_INFO.name)
        data object SeekHelpInfo : UnauthenticatedScreen(Routes.SEEK_HELP_INFO.name)
    }
}