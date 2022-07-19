package com.sparjapati.weatherApp.domain.repository

import com.sparjapati.weatherApp.data.remote.WeatherDto
import com.sparjapati.weatherApp.domain.util.Resource
import com.sparjapati.weatherApp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lng: Double): Resource<WeatherInfo>
}