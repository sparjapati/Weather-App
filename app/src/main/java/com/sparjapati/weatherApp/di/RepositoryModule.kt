package com.sparjapati.weatherApp.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sparjapati.weatherApp.data.remote.WeatherApi
import com.sparjapati.weatherApp.data.repository.WeatherRepoImpl
import com.sparjapati.weatherApp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Provides
    @Singleton
    abstract fun bindWeatherRepository(weatherRepoImpl: WeatherRepoImpl): WeatherRepository
}