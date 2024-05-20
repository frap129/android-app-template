package dev.maples.template.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import dev.maples.template.ui.R

fun getColorScheme(accent: Color, backgroundLight: Color, backgroundMid: Color, backgroundDark: Color) = darkColorScheme(
    primary = backgroundMid,
    onPrimary = accent,
    primaryContainer = backgroundLight,
    onPrimaryContainer = accent,
    secondary = backgroundMid,
    onSecondary = accent,
    secondaryContainer = backgroundLight,
    onSecondaryContainer = accent,
    tertiary = backgroundMid,
    onTertiary = accent,
    tertiaryContainer = backgroundLight,
    onTertiaryContainer = accent,
    background = backgroundDark,
    onBackground = accent,
    surface = backgroundMid,
    onSurface = accent,
    surfaceVariant = backgroundLight,
    onSurfaceVariant = accent
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), dynamicColor: Boolean = true, content: @Composable () -> Unit) {
    val view = LocalView.current
    val colorScheme = getColorScheme(
        colorResource(id = R.color.accent),
        colorResource(id = R.color.backgroundLight),
        colorResource(id = R.color.backgroundMid),
        colorResource(id = R.color.backgroundDark)
    )
    val typography = getTypography()

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
