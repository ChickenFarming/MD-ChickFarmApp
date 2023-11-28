package com.dicoding.chickfarm.ui.screen.map



import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapViewModel() : ViewModel() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var context: Context

    fun init(context: Context, requestPermissionLauncher: ActivityResultLauncher<Array<String>>) {
        this.requestPermissionLauncher = requestPermissionLauncher
        this.context = context
    }


    fun checkAndRequestPermissions() {

//                // Izin belum diberikan, minta izin
                requestPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ))

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

            // Mendaftarkan pendengar untuk location updates
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            p0?.lastLocation?.let{
                val userLocation = LatLng(it.latitude, it.longitude)
                setUserLocation(userLocation)


            }
        }
    }


        private fun addMarkersWithKeyword(keyword: String, userLocation: LatLng) {
        // Buat permintaan Places API
        Places.initialize(context, "AIzaSyDNP8EFQK80AqDkw7RFqxldMcyPBfDgJT0")
        val placesClient: PlacesClient = Places.createClient(context)

        // Buat permintaan untuk mencari tempat terdekat berdasarkan koordinat pengguna
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(keyword)
            .setLocationBias(
                RectangularBounds.newInstance(
                    LatLng(userLocation.latitude - 0.1, userLocation.longitude - 0.1),
                    LatLng(userLocation.latitude + 0.1, userLocation.longitude + 0.1)
                )
            )
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                // Pastikan ada hasil pencarian sebelum menambahkan marker
                if (response.autocompletePredictions.isNotEmpty()) {
                    val prediction = response.autocompletePredictions[0]
                    val placeId = prediction.placeId

                    val placeRequest = FetchPlaceRequest.builder(placeId, listOf(Place.Field.LAT_LNG)).build()

                    placesClient.fetchPlace(placeRequest)
                        .addOnSuccessListener { placeResponse ->
                            val place = placeResponse.place
                            val placeLatLng = place.latLng

                            Log.d("MainActivity", "Adding marker for nearest place: $place")
                            // Tambahkan marker ke peta
                            addMarker(MarkerOptions().position(placeLatLng).title(place.name))
                        }
                        .addOnFailureListener { exception ->
                            Log.e("MainActivity", "Failed to fetch place details: ${exception.message}")
                        }
                } else {
                    Log.d("MainActivity", "Tidak ada hasil pencarian tempat terdekat.")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Failed to fetch autocomplete predictions: ${exception.message}")
            }
    }


    // Gunakan MutableState untuk menyimpan lokasi pengguna
    val userLocation = mutableStateOf(LatLng(-5.135399, 119.423790))
    var cameraPosition = mutableStateOf( CameraPosition.fromLatLngZoom(LatLng(-5.135399, 119.423790), 10f))
    val markers: MutableState<List<MarkerOptions>> = mutableStateOf(emptyList())
    // Fungsi untuk menambahkan marker ke daftar markers
    fun addMarker(marker: MarkerOptions) {
        markers.value = markers.value + marker
    }

    fun setUserLocation(location: LatLng) {
        userLocation.value = location
    }
    fun moveCameraToLocation(location: LatLng) {
        cameraPosition.value = CameraPosition.fromLatLngZoom(location, 17f)
    }






}