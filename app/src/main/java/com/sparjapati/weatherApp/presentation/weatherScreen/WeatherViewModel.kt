package com.sparjapati.weatherApp.presentation.weatherScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparjapati.weatherApp.domain.location.LocationTracker
import com.sparjapati.weatherApp.domain.repository.WeatherRepository
import com.sparjapati.weatherApp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    init {
        loadWeatherInfo()
    }

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            locationTracker.getCurrentLocation().let { locationResource ->
                when (locationResource) {
                    is Resource.Error -> {
                        state = state.copy(error = locationResource.message, isLoading = false, weatherInfo = null)
                    }
                    is Resource.Success -> {
                        locationResource.data?.let { location ->
                            state = when (val result = repository.getWeatherData(location.latitude, location.longitude)) {
                                is Resource.Error -> {
                                    state.copy(error = result.message, isLoading = false, weatherInfo = null)
                                }
                                is Resource.Success -> {
                                    state.copy(weatherInfo = result.data, isLoading = false, error = null)
                                }
                            }
                        } ?: run {
                            state.copy(error = locationResource.message, isLoading = false, weatherInfo = null)
                        }
                    }
                }
            }
        }
    }
}