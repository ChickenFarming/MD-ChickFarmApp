package com.dicoding.chickfarm.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier.fillMaxSize().padding(60.dp)


    ){

        Text(
            text = "Camera",
            modifier = Modifier
                .align(Alignment.CenterHorizontally).padding(5.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,

                ),
        )
    }
}