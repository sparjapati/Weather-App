package com.sparjapati.weatherApp.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sparjapati.weatherApp.presentation.ui.theme.DarkBlue
import com.sparjapati.weatherApp.presentation.ui.theme.DeepBlue
import com.sparjapati.weatherApp.presentation.ui.theme.WeatherAppTheme
import com.sparjapati.weatherApp.presentation.weatherScreen.WeatherViewModel
import com.sparjapati.weatherApp.presentation.weatherScreen.components.WeatherCard
import com.sparjapati.weatherApp.presentation.weatherScreen.components.WeatherForecast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        viewModel.loadWeatherInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ))
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                WeatherAppTheme {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue)
                    ) {
                        WeatherCard(
                            state = viewModel.state,
                            backgroundColor = DeepBlue
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        WeatherForecast(state = viewModel.state)
                    }
                }
                if (viewModel.state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                viewModel.state.error?.let { message ->
                    Text(
                        text = message,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}