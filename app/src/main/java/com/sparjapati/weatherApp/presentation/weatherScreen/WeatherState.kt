package com.sparjapati.weatherApp.presentation.weatherScreen

import com.sparjapati.weatherApp.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
