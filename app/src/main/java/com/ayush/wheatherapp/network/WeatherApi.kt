package com.ayush.wheatherapp.network
import com.ayush.wheatherapp.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("hourly") hourly: String = "temperature_2m",
        @Query("timezone") timezone: String = "GMT"
    ): WeatherResponse
}