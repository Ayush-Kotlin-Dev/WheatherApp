package com.ayush.wheatherapp.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WeatherCard(
    dateTime: String,
    temperature: Double,
    unit: String,
    modifier: Modifier = Modifier,
    index: Int = 0
) {
    var isExpanded: Boolean by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                temperature < 0 -> Color(0xFFE3F2FD)
                temperature < 10 -> Color(0xFFFFFFFF)
                temperature < 20 -> Color(0xFFFFF3E0)
                else -> Color(0xFFFFECB3)
            }
        ),
        onClick = { isExpanded = !isExpanded }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    val localDateTime = LocalDateTime.parse(dateTime)
                    Text(
                        text = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = localDateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                AnimatedContent(
                    targetState = temperature,
                    transitionSpec = {
                        slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()
                    }
                ) { targetTemp ->
                    Text(
                        text = "${String.format("%.1f", targetTemp)}$unit",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            targetTemp < 0 -> Color(0xFF1565C0)
                            targetTemp < 10 -> Color(0xFF1976D2)
                            targetTemp < 20 -> Color(0xFFFF9800)
                            else -> Color(0xFFF57C00)
                        }
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    WeatherIcon(temperature)
                    Text(
                        text = getWeatherDescription(temperature),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherIcon(temperature: Double) {
    Text(
        text = when {
            temperature < 0 -> "❄️"
            temperature < 10 -> "🌥️"
            temperature < 20 -> "☀️"
            else -> "🌡️"
        },
        fontSize = 24.sp,
        modifier = Modifier.padding(top = 4.dp)
    )
}

private fun getWeatherDescription(temperature: Double): String = when {
    temperature < 0 -> "Freezing cold"
    temperature < 10 -> "Quite chilly"
    temperature < 20 -> "Pleasant weather"
    else -> "Warm weather"
}