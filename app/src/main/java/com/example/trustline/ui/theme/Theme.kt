package com.example.trustline.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@Immutable
data class ExtendedColorScheme(
    val accent1: ColorFamily,
    val accent2: ColorFamily,
    val accent3: ColorFamily,
    val accent4: ColorFamily,
    val accent5: ColorFamily,
    val accent6: ColorFamily,
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
//    surfaceDim = surfaceDimLight,
//    surfaceBright = surfaceBrightLight,
//    surfaceContainerLowest = surfaceContainerLowestLight,
//    surfaceContainerLow = surfaceContainerLowLight,
//    surfaceContainer = surfaceContainerLight,
//    surfaceContainerHigh = surfaceContainerHighLight,
//    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
//    surfaceDim = surfaceDimDark,
//    surfaceBright = surfaceBrightDark,
//    surfaceContainerLowest = surfaceContainerLowestDark,
//    surfaceContainerLow = surfaceContainerLowDark,
//    surfaceContainer = surfaceContainerDark,
//    surfaceContainerHigh = surfaceContainerHighDark,
//    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
//    surfaceDim = surfaceDimLightMediumContrast,
//    surfaceBright = surfaceBrightLightMediumContrast,
//    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
//    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
//    surfaceContainer = surfaceContainerLightMediumContrast,
//    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
//    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
//    surfaceDim = surfaceDimLightHighContrast,
//    surfaceBright = surfaceBrightLightHighContrast,
//    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
//    surfaceContainerLow = surfaceContainerLowLightHighContrast,
//    surfaceContainer = surfaceContainerLightHighContrast,
//    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
//    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
//    surfaceDim = surfaceDimDarkMediumContrast,
//    surfaceBright = surfaceBrightDarkMediumContrast,
//    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
//    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
//    surfaceContainer = surfaceContainerDarkMediumContrast,
//    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
//    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
//    surfaceDim = surfaceDimDarkHighContrast,
//    surfaceBright = surfaceBrightDarkHighContrast,
//    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
//    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
//    surfaceContainer = surfaceContainerDarkHighContrast,
//    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
//    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

val extendedLight = ExtendedColorScheme(
    accent1 = ColorFamily(
        accent1Light,
        onAccent1Light,
        accent1ContainerLight,
        onAccent1ContainerLight,
    ),
    accent2 = ColorFamily(
        accent2Light,
        onAccent2Light,
        accent2ContainerLight,
        onAccent2ContainerLight,
    ),
    accent3 = ColorFamily(
        accent3Light,
        onAccent3Light,
        accent3ContainerLight,
        onAccent3ContainerLight,
    ),
    accent4 = ColorFamily(
        accent4Light,
        onAccent4Light,
        accent4ContainerLight,
        onAccent4ContainerLight,
    ),
    accent5 = ColorFamily(
        accent5Light,
        onAccent5Light,
        accent5ContainerLight,
        onAccent5ContainerLight,
    ),
    accent6 = ColorFamily(
        accent6Light,
        onAccent6Light,
        accent6ContainerLight,
        onAccent6ContainerLight,
    ),
)

val extendedDark = ExtendedColorScheme(
    accent1 = ColorFamily(
        accent1Dark,
        onAccent1Dark,
        accent1ContainerDark,
        onAccent1ContainerDark,
    ),
    accent2 = ColorFamily(
        accent2Dark,
        onAccent2Dark,
        accent2ContainerDark,
        onAccent2ContainerDark,
    ),
    accent3 = ColorFamily(
        accent3Dark,
        onAccent3Dark,
        accent3ContainerDark,
        onAccent3ContainerDark,
    ),
    accent4 = ColorFamily(
        accent4Dark,
        onAccent4Dark,
        accent4ContainerDark,
        onAccent4ContainerDark,
    ),
    accent5 = ColorFamily(
        accent5Dark,
        onAccent5Dark,
        accent5ContainerDark,
        onAccent5ContainerDark,
    ),
    accent6 = ColorFamily(
        accent6Dark,
        onAccent6Dark,
        accent6ContainerDark,
        onAccent6ContainerDark,
    ),
)

val extendedLightMediumContrast = ExtendedColorScheme(
    accent1 = ColorFamily(
        accent1LightMediumContrast,
        onAccent1LightMediumContrast,
        accent1ContainerLightMediumContrast,
        onAccent1ContainerLightMediumContrast,
    ),
    accent2 = ColorFamily(
        accent2LightMediumContrast,
        onAccent2LightMediumContrast,
        accent2ContainerLightMediumContrast,
        onAccent2ContainerLightMediumContrast,
    ),
    accent3 = ColorFamily(
        accent3LightMediumContrast,
        onAccent3LightMediumContrast,
        accent3ContainerLightMediumContrast,
        onAccent3ContainerLightMediumContrast,
    ),
    accent4 = ColorFamily(
        accent4LightMediumContrast,
        onAccent4LightMediumContrast,
        accent4ContainerLightMediumContrast,
        onAccent4ContainerLightMediumContrast,
    ),
    accent5 = ColorFamily(
        accent5LightMediumContrast,
        onAccent5LightMediumContrast,
        accent5ContainerLightMediumContrast,
        onAccent5ContainerLightMediumContrast,
    ),
    accent6 = ColorFamily(
        accent6LightMediumContrast,
        onAccent6LightMediumContrast,
        accent6ContainerLightMediumContrast,
        onAccent6ContainerLightMediumContrast,
    ),
)

val extendedLightHighContrast = ExtendedColorScheme(
    accent1 = ColorFamily(
        accent1LightHighContrast,
        onAccent1LightHighContrast,
        accent1ContainerLightHighContrast,
        onAccent1ContainerLightHighContrast,
    ),
    accent2 = ColorFamily(
        accent2LightHighContrast,
        onAccent2LightHighContrast,
        accent2ContainerLightHighContrast,
        onAccent2ContainerLightHighContrast,
    ),
    accent3 = ColorFamily(
        accent3LightHighContrast,
        onAccent3LightHighContrast,
        accent3ContainerLightHighContrast,
        onAccent3ContainerLightHighContrast,
    ),
    accent4 = ColorFamily(
        accent4LightHighContrast,
        onAccent4LightHighContrast,
        accent4ContainerLightHighContrast,
        onAccent4ContainerLightHighContrast,
    ),
    accent5 = ColorFamily(
        accent5LightHighContrast,
        onAccent5LightHighContrast,
        accent5ContainerLightHighContrast,
        onAccent5ContainerLightHighContrast,
    ),
    accent6 = ColorFamily(
        accent6LightHighContrast,
        onAccent6LightHighContrast,
        accent6ContainerLightHighContrast,
        onAccent6ContainerLightHighContrast,
    ),
)

val extendedDarkMediumContrast = ExtendedColorScheme(
    accent1 = ColorFamily(
        accent1DarkMediumContrast,
        onAccent1DarkMediumContrast,
        accent1ContainerDarkMediumContrast,
        onAccent1ContainerDarkMediumContrast,
    ),
    accent2 = ColorFamily(
        accent2DarkMediumContrast,
        onAccent2DarkMediumContrast,
        accent2ContainerDarkMediumContrast,
        onAccent2ContainerDarkMediumContrast,
    ),
    accent3 = ColorFamily(
        accent3DarkMediumContrast,
        onAccent3DarkMediumContrast,
        accent3ContainerDarkMediumContrast,
        onAccent3ContainerDarkMediumContrast,
    ),
    accent4 = ColorFamily(
        accent4DarkMediumContrast,
        onAccent4DarkMediumContrast,
        accent4ContainerDarkMediumContrast,
        onAccent4ContainerDarkMediumContrast,
    ),
    accent5 = ColorFamily(
        accent5DarkMediumContrast,
        onAccent5DarkMediumContrast,
        accent5ContainerDarkMediumContrast,
        onAccent5ContainerDarkMediumContrast,
    ),
    accent6 = ColorFamily(
        accent6DarkMediumContrast,
        onAccent6DarkMediumContrast,
        accent6ContainerDarkMediumContrast,
        onAccent6ContainerDarkMediumContrast,
    ),
)

val extendedDarkHighContrast = ExtendedColorScheme(
    accent1 = ColorFamily(
        accent1DarkHighContrast,
        onAccent1DarkHighContrast,
        accent1ContainerDarkHighContrast,
        onAccent1ContainerDarkHighContrast,
    ),
    accent2 = ColorFamily(
        accent2DarkHighContrast,
        onAccent2DarkHighContrast,
        accent2ContainerDarkHighContrast,
        onAccent2ContainerDarkHighContrast,
    ),
    accent3 = ColorFamily(
        accent3DarkHighContrast,
        onAccent3DarkHighContrast,
        accent3ContainerDarkHighContrast,
        onAccent3ContainerDarkHighContrast,
    ),
    accent4 = ColorFamily(
        accent4DarkHighContrast,
        onAccent4DarkHighContrast,
        accent4ContainerDarkHighContrast,
        onAccent4ContainerDarkHighContrast,
    ),
    accent5 = ColorFamily(
        accent5DarkHighContrast,
        onAccent5DarkHighContrast,
        accent5ContainerDarkHighContrast,
        onAccent5ContainerDarkHighContrast,
    ),
    accent6 = ColorFamily(
        accent6DarkHighContrast,
        onAccent6DarkHighContrast,
        accent6ContainerDarkHighContrast,
        onAccent6ContainerDarkHighContrast,
    ),
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun TrustlineTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

/**
 * Sets up edge-to-edge for the window of this [view]. The system icon colors are set to either
 * light or dark depending on whether the [darkTheme] is enabled or not.
 */
private fun setUpEdgeToEdge(view: View, darkTheme: Boolean) {
    val window = (view.context as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.Transparent.toArgb()
    val navigationBarColor = when {
        Build.VERSION.SDK_INT >= 29 -> Color.Transparent.toArgb()
        Build.VERSION.SDK_INT >= 26 -> Color(0xFF, 0xFF, 0xFF, 0x63).toArgb()
        // Min sdk version for this app is 24, this block is for SDK versions 24 and 25
        else -> Color(0x00, 0x00, 0x00, 0x50).toArgb()
    }
    window.navigationBarColor = navigationBarColor
    val controller = WindowCompat.getInsetsController(window, view)
    controller.isAppearanceLightStatusBars = !darkTheme
    controller.isAppearanceLightNavigationBars = !darkTheme
}

