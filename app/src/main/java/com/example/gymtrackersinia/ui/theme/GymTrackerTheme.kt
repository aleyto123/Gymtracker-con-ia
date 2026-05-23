package com.example.gymtrackersinia.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GymColorScheme = darkColorScheme(
    primary = Color(0xFFFFD166),
    onPrimary = Color(0xFF231B00),
    secondary = Color(0xFF59F8B6),
    onSecondary = Color(0xFF002116),
    tertiary = Color(0xFFFF8CC6),
    background = Color(0xFF101227),
    onBackground = Color(0xFFF8F7FF),
    surface = Color(0xFF1B1E3A),
    onSurface = Color(0xFFF8F7FF),
    surfaceVariant = Color(0xFF293052),
    onSurfaceVariant = Color(0xFFDDE1FF),
    error = Color(0xFFFF6B6B),
    onError = Color(0xFF310000)
)

@Composable
fun GymTrackerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GymColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
