package com.ayush.wheatherapp.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ayush.wheatherapp.WeatherUiState
import com.ayush.wheatherapp.WeatherViewModel
import com.ayush.wheatherapp.components.WeatherCard
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeatherData()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        SmallTopAppBar(
            title = {
                Text(
                    "Weather Forecast",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is WeatherUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                is WeatherUiState.Success -> {
                    val data = (uiState as WeatherUiState.Success).data
                    val groupedData = data.hourly.time.zip(data.hourly.temperature2m)
                        .groupBy { it.first.substringBefore("T") }

                    LazyColumn {
                        groupedData.forEach { (date, hourlyData) ->
                            item {
                                Text(
                                    text = formatDate(date),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(16.dp, 8.dp)
                                )
                            }
                            itemsIndexed(hourlyData) { index, (time, temp) ->
                                this@Column.AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn() + slideInVertically(
                                        initialOffsetY = { it * (index % 3 + 1) }
                                    )
                                ) {
                                    WeatherCard(
                                        dateTime = time,
                                        temperature = temp,
                                        unit = data.hourlyUnits.temperature2m,
                                        index = index
                                    )
                                }
                            }
                        }
                    }
                }
                is WeatherUiState.Error -> {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = "Error: ${(uiState as WeatherUiState.Error).message}",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}

fun formatDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = java.time.LocalDate.parse(dateString, formatter)
    return date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d"))
}