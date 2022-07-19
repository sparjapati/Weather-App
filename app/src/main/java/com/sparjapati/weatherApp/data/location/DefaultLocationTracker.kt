package com.sparjapati.weatherApp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.sparjapati.weatherApp.domain.location.LocationTracker
import com.sparjapati.weatherApp.domain.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val app: Application,
) : LocationTracker {
    override suspend fun getCurrentLocation(): Resource<Location> {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(app, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(app, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val locationManager = app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasFineLocationPermission || !hasCoarseLocationPermission)
            return Resource.Error("Location Permission not Granted")
        if (!isGpsEnabled)
            return Resource.Error("GPS not enabled")
        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful)
                        cont.resume(if (result != null) Resource.Success(result) else Resource.Error("Unknown Error Occurred"))
                    else
                        cont.resume(Resource.Error("Couldn't retrieve location. Make sure to grant permission and turn on GPS"))
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(Resource.Success(it))
                }
                addOnFailureListener {
                    cont.resume(Resource.Error(it.message ?: "Unknown Error Occurred"))
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }
}