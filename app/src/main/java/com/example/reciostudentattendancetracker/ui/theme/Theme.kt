package com.example.reciostudentattendancetracker.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightGreenColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = TextOnPrimary,
    primaryContainer = PrimaryGreenLight,
    onPrimaryContainer = PrimaryGreenDark,

    secondary = SecondaryGreen,
    onSecondary = TextOnPrimary,
    secondaryContainer = SecondaryGreenLight,
    onSecondaryContainer = SecondaryGreenDark,

    tertiary = AccentMint,
    onTertiary = TextOnPrimary,
    tertiaryContainer = TertiaryGreenLight,
    onTertiaryContainer = TertiaryGreenDark,

    error = ErrorRed,
    onError = TextOnPrimary,

    background = BackgroundLight,
    onBackground = TextPrimary,

    surface = SurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = TertiaryGreenLight,
    onSurfaceVariant = TextSecondary,

    outline = SecondaryGreen,
    outlineVariant = AccentLime
)

private val DarkGreenColorScheme = darkColorScheme(
    primary = SecondaryGreen,
    onPrimary = PrimaryGreenDark,
    primaryContainer = PrimaryGreenDark,
    onPrimaryContainer = SecondaryGreenLight,

    secondary = TertiaryGreen,
    onSecondary = PrimaryGreenDark,
    secondaryContainer = SecondaryGreenDark,
    onSecondaryContainer = TertiaryGreenLight,

    tertiary = AccentMint,
    onTertiary = PrimaryGreenDark,
    tertiaryContainer = TertiaryGreenDark,
    onTertiaryContainer = TertiaryGreenLight,

    error = ErrorRed,
    onError = TextOnPrimary,

    background = BackgroundDark,
    onBackground = TextOnPrimary,

    surface = SurfaceDark,
    onSurface = TextOnPrimary,
    surfaceVariant = TertiaryGreenDark,
    onSurfaceVariant = AccentLime,

    outline = AccentLime,
    outlineVariant = SecondaryGreen
)

@Composable
fun RecioStudentAttendanceTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Changed to false to use our custom green theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkGreenColorScheme
        else -> LightGreenColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}