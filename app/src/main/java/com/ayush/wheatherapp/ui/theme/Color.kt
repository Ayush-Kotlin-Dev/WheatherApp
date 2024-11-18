package com.ayush.wheatherapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1B3B5A),
    secondary = Color(0xFF2196F3),
    tertiary = Color(0xFF03A9F4),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
)

@Composable
fun WeatherAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}