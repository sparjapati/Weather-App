package com.sparjapati.weatherApp.data.repository

import android.util.Log
import com.sparjapati.weatherApp.data.mappers.toWeatherInfo
import com.sparjapati.weatherApp.data.remote.WeatherApi
import com.sparjapati.weatherApp.domain.repository.WeatherRepository
import com.sparjapati.weatherApp.domain.util.Resource
import com.sparjapati.weatherApp.domain.weather.WeatherInfo
import java.lang.Exception
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(private val api: WeatherApi) : WeatherRepository {
    
    companion object {
        const val TAG = "WeatherRepository"
    }

    override suspend fun getWeatherData(lat: Double, lng: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                api.getWeatherData(lat, lng).toWeatherInfo())
        } catch (e: Exception) {
            Log.d(TAG, "Getting Weather Data Error: ${e.message}")
            Resource.Error(e.message ?: "Unknown Error Occurred")
        }
    }
}