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
    SEEK_HELP_INFO,
    LOGIN_AWARE,
    CASES,
    ZEN,
    CHAT,
    PROFILE,
    REPORT,
    GBV_RESOURCES,
    GBV_RESOURCES_DETAIL,
    AUTH_REPORT_CASE
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
        data object LoginAware : UnauthenticatedScreen(Routes.LOGIN_AWARE.name)

        data object Cases : UnauthenticatedScreen(Routes.CASES.name)
        data object Zen : UnauthenticatedScreen(Routes.ZEN.name)
        data object Chat : UnauthenticatedScreen(Routes.CHAT.name)
        data object Profile : UnauthenticatedScreen(Routes.PROFILE.name)
        data object Report : UnauthenticatedScreen(Routes.REPORT.name)

        data object GbvResources : UnauthenticatedScreen(Routes.GBV_RESOURCES.name)
        data object AuthReportCase : UnauthenticatedScreen(Routes.AUTH_REPORT_CASE.name)
        data object GbvResourcesDetail : UnauthenticatedScreen(Routes.GBV_RESOURCES_DETAIL.name)
    }
}