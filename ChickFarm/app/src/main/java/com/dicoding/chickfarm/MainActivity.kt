package com.dicoding.chickfarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dicoding.chickfarm.ui.ChickFarmApp
import com.dicoding.chickfarm.ui.theme.ChickFarmTheme
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.dicoding.chickfarm.ui.screen.map.MapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient


class MainActivity : ComponentActivity() {

    private val viewModel: MapViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        checkAndRequestPermissions()
        setContent {
            ChickFarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChickFarmApp(viewModel = viewModel)
                }
            }
        }
    }




    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                        permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                    // Izin diberikan, dapatkan lokasi
                    getMyLastLocation()
                }
                else -> {
                    // Izin ditolak, berikan pesan atau ambil tindakan yang sesuai
                }
            }
        }

    private fun checkAndRequestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED -> {
                // Izin sudah diberikan, dapatkan lokasi
                getMyLastLocation()

            }
            else -> {
                // Izin belum diberikan, minta izin
                requestPermissionLauncher.launch(permissions)
            }
        }
    }


    private fun getMyLastLocation() {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        try {
            val locationTask = fusedLocationClient.lastLocation
            locationTask.addOnSuccessListener { location ->
                location?.let {
                    // Lokasi ditemukan, lakukan sesuatu dengan lokasi tersebut
                    val userLocation = LatLng(it.latitude, it.longitude)
                    viewModel.setUserLocation(userLocation)

                    Log.d("MainActivity", "User location: $userLocation")
                    addMarkersWithKeyword("Food", userLocation)

                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }


    private fun addMarkersWithKeyword(keyword: String, userLocation: LatLng) {
        // Buat permintaan Places API
        Places.initialize(applicationContext, "AIzaSyDNP8EFQK80AqDkw7RFqxldMcyPBfDgJT0")
        val placesClient: PlacesClient = Places.createClient(this)

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
                            viewModel.addMarker(MarkerOptions().position(placeLatLng).title(place.name))
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



}

