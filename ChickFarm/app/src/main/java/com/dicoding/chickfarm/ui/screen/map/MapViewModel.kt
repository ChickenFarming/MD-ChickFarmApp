package com.dicoding.chickfarm.ui.screen.map



import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapViewModel() : ViewModel() {
    // Gunakan MutableState untuk menyimpan lokasi pengguna
    val userLocation = mutableStateOf(LatLng(0.0, 0.0))

    val markers: MutableState<List<MarkerOptions>> = mutableStateOf(emptyList())
    // Fungsi untuk menambahkan marker ke daftar markers
    fun addMarker(marker: MarkerOptions) {
        markers.value = markers.value + marker
    }

    fun setUserLocation(location: LatLng) {
        userLocation.value = location
    }






}