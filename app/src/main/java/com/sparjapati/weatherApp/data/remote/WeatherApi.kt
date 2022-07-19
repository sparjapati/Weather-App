package com.sparjapati.weatherApp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val BASE_URL = "https:api.open-meteo.com/"
    }

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
    ): WeatherDto
}