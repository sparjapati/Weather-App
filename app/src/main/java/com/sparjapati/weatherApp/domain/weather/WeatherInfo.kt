package com.sparjapati.weatherApp.domain.weather

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currWeatherData: WeatherData?,
)
