package com.dicoding.chickfarm.ui.screen.map


import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.widget.Toast

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.dicoding.chickfarm.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.maps.android.compose.CameraPositionState

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    context: Context
) {
    val viewModel: MapViewModel = viewModel()


    var conditionState by remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        when {
            permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                // Izin diberikan, dapatkan lokasi
                Toast.makeText(context, "fgAGA", Toast.LENGTH_SHORT).show()
                viewModel.startLocationUpdates()
            }

            else -> {
                // Izin ditolak, berikan pesan atau ambil tindakan yang sesuai
            }
        }
    }


    DisposableEffect(conditionState) {
        viewModel.init(context, requestPermissionLauncher)
        viewModel.checkAndRequestPermissions()
        onDispose { }
    }

    Column{

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(16.dp)),
            cameraPositionState = CameraPositionState(viewModel.cameraPosition.value)
        ) {

            Marker(
                state = MarkerState(viewModel.userLocation.value),
                title = stringResource(id = R.string.my_location),
            )
//
//            viewModel.markers.value.forEach { markerOptions ->
//                Marker(
//                    state = MarkerState(position = markerOptions.position),
//                    title = markerOptions.title,
//                )
//            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                          viewModel.moveCameraToLocation(viewModel.userLocation.value)
//                    if (conditionState) {
//                        conditionState = false
//                    } else {
//                        conditionState = true
//                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    ,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Fokus ke lokasi ku")
            }
        }
    }
    }



}




























