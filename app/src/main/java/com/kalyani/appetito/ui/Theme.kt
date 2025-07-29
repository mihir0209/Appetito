package ui

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- Color Definitions ---
val DarkBlue = Color(0xFF002244)
val MidnightBlue = Color(0xFF0A2342)
private val LightColors = lightColorScheme(
    primary = Color(0xFFFE724C),    // Main brand color
    onPrimary = Color.White,        // Text/icons on primary color
    background = Color.White,       // Screen backgrounds
    onBackground = Color.Black,     // Main text color
    surface = Color(0xFFF5F5F5),    // Card backgrounds, text fields
    onSurface = Color.Black,        // Text on cards
    surfaceVariant = Color(0xFFE0E0E0), // Dividers, borders
    onSurfaceVariant = Color.Gray   // Subtle text
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFFE724C),      // Main brand color remains vibrant
    onPrimary = Color.Black,
    background = Color(0xFF121212),   // Dark background
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),      // Dark cards
    onSurface = Color.White,
    surfaceVariant = Color(0xFF424242),
    onSurfaceVariant = Color(0xFFBDBDBD)
)

// --- Main Theme Composable ---

@Composable
fun AppetitoTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.background.toArgb() // Set status bar color
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(), // You can define custom typography here
        content = content
    )
}