package com.sparjapati.weatherApp.domain.location

import android.location.Location
import com.sparjapati.weatherApp.domain.util.Resource

interface LocationTracker {
    suspend fun getCurrentLocation(): Resource<Location>
}