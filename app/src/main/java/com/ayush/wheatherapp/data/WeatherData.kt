package com.ayush.wheatherapp.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    @SerializedName("hourly") val hourly: HourlyData,
    @SerializedName("hourly_units") val hourlyUnits: HourlyUnits
)

data class HourlyData(
    val time: List<String>,
    @SerializedName("temperature_2m") val temperature2m: List<Double>
)

data class HourlyUnits(
    @SerializedName("temperature_2m") val temperature2m: String
)