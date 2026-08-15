package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
      primary = VideoHubRed,
      secondary = VideoHubDarkRed,
      background = BackgroundDark,
      surface = SurfaceDark,
      onPrimary = Color.White,
      onSecondary = Color.White,
      onBackground = TextPrimaryDark,
      onSurface = TextPrimaryDark,
      surfaceVariant = SurfaceDark,
      onSurfaceVariant = TextSecondaryDark
  )

private val LightColorScheme =
  lightColorScheme(
      primary = VideoHubRed,
      secondary = VideoHubDarkRed,
      background = BackgroundLight,
      surface = SurfaceLight,
      onPrimary = Color.White,
      onSecondary = Color.White,
      onBackground = TextPrimaryLight,
      onSurface = TextPrimaryLight,
      surfaceVariant = SurfaceLight,
      onSurfaceVariant = TextSecondaryLight
  )

@Composable
fun VideoHubTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Disabling dynamic color to enforce brand identity
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
