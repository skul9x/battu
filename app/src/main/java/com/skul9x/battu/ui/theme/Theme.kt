package com.skul9x.battu.ui.theme

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Light Color Scheme - Eastern Traditional Theme
 * Based on rice paper aesthetics with Ngũ Hành accent colors
 */
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    
    background = Background,
    onBackground = OnBackground,
    
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    
    outline = Outline,
    outlineVariant = OutlineVariant
)

/**
 * Dark Color Scheme - Deep Charcoal with Gold accents
 * Optional for night mode usage
 */
private val DarkColorScheme = darkColorScheme(
    primary = ElementFire,
    onPrimary = OnPrimary,
    primaryContainer = ElementFireDark,
    onPrimaryContainer = ElementFireLight,
    
    secondary = AccentGoldDark,
    onSecondary = TextPrimaryInk,
    secondaryContainer = AccentGold,
    onSecondaryContainer = TextDarkPrimary,
    
    tertiary = ElementWood,
    onTertiary = OnTertiary,
    tertiaryContainer = ElementWoodDark,
    onTertiaryContainer = ElementWoodLight,
    
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    
    background = BackgroundDark,
    onBackground = TextDarkPrimary,
    
    surface = SurfaceDark,
    onSurface = TextDarkPrimary,
    surfaceVariant = BackgroundDark,
    onSurfaceVariant = TextDarkPrimary,
    
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F)
)

/**
 * BatTu Theme
 * 
 * Applies Eastern Traditional design system with Ngũ Hành colors
 * 
 * @param darkTheme Whether to use dark theme (default: system setting)
 * @param dynamicColor Whether to use dynamic color (Android 12+, default: false for consistent branding)
 * @param content Composable content
 */
@Composable
fun BatTuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+, but we disable by default
    // to maintain consistent Eastern Traditional aesthetics
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
