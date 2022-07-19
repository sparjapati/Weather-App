package com.sparjapati.weatherApp.data.mappers

import com.sparjapati.weatherApp.data.remote.WeatherDto
import com.sparjapati.weatherApp.domain.weather.WeatherData
import com.sparjapati.weatherApp.domain.weather.WeatherInfo
import com.sparjapati.weatherApp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData,
)

fun WeatherDto.WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, timeString ->
        val temperature = temperature2m[index]
        val pressure = pressureMsl[index]
        val humidity = relativehumidity2m[index]
        val weatherCode = weathercode[index]
        val windSpeed = windspeed10m[index]
        val t = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_DATE_TIME)
        IndexedWeatherData(index,
            WeatherData(t, temperature, pressure, windSpeed, humidity, WeatherType.fromWMO(weatherCode)))
    }.groupBy {
        it.index / 24
    }.mapValues { entry ->
        entry.value.map {
            it.data
        }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherDataDto.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(weatherDataMap, currWeatherData)
}