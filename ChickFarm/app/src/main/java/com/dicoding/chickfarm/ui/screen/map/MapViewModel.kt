package com.dicoding.chickfarm.ui.screen.map


import android.Manifest
import android.content.Context
import android.os.Looper
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dicoding.chickfarm.data.MarkerData
import com.dicoding.chickfarm.data.MarkerList
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class MapViewModel() : ViewModel() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var context: Context

//    init requestPermissionLauncher
    fun init(context: Context, requestPermissionLauncher: ActivityResultLauncher<Array<String>>) {
        this.requestPermissionLauncher = requestPermissionLauncher
        this.context = context
    }


    fun checkAndRequestPermissions() {
//                // Izin belum diberikan, minta izin
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

    }

    fun startLocationUpdates() {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        try {
            // Konfigurasi request location updates
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 100
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            p0?.lastLocation?.let {
                val userLocation = LatLng(it.latitude, it.longitude)
                setUserLocation(userLocation)


            }
        }
    }


    val userLocation = mutableStateOf(LatLng(-5.135399, 119.423790))
    var cameraPosition =
        mutableStateOf(CameraPosition.fromLatLngZoom(LatLng(-5.135399, 119.423790), 10f))
    val markerList = mutableListOf<MarkerList>()

    init {
        if (markerList.isEmpty()) {
            MarkerData.marker.forEach {
                markerList.add(MarkerList(it))
            }
        }
    }

    fun setUserLocation(location: LatLng) {
        userLocation.value =location
    }

    fun moveCameraToLocation(location: LatLng) {
        cameraPosition.value = CameraPosition.fromLatLngZoom(location, 15f)
    }


}