package com.dicoding.chickfarm.ui.screen.map


import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Tasks
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = viewModel()
) {


    var userLocation by remember { mutableStateOf(LatLng(1.35, 103.87)) }

    val mapContext = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(mapContext)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 15f)
    }


    LaunchedEffect(true) {
        // Mendapatkan lokasi pengguna saat aplikasi dimulai
        withContext(Dispatchers.IO) {
            try {
                val locationTask = fusedLocationClient.lastLocation
                val location = Tasks.await(locationTask)
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                    viewModel.userLocation.value = userLocation
                    withContext(Dispatchers.Main) {
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            userLocation,
                            15f
                        )
                    }
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp)),
        cameraPositionState = cameraPositionState
    ) {

        Marker(
            state = MarkerState(viewModel.userLocation.value),
            title = "Lokasi Ku",
        )

//        viewModel.markers.value.forEach { markerOptions ->
//            Marker(
//                state = MarkerState(position = markerOptions.position),
//                title = markerOptions.title,
//            )
//        }
    }
}
























