package com.dicoding.chickfarm.ui.screen.diseasedetector

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dicoding.chickfarm.R

@Composable
fun DiseaseDetectorScreen(
    context: Context,
    modifier: Modifier,
//    navigateToTakeImage:() -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        DiseaseDetectorHeadline(modifier)

        Image(
            painter = painterResource(R.drawable.logo_disease_detector),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
//                .clip(Shapes.medium)
        )

        Button(
            onClick = {
                val intent = Intent(context, TakeImageActivity::class.java)
                context.startActivity(intent)
//                      navigateToTakeImage()
            },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
            ,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Ambil Gambar")
        }
        
        Spacer(modifier = Modifier.height(30.dp))
    }

}

@Composable
fun DiseaseDetectorHeadline(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(250.dp)
    ) {
        Text(
            text = stringResource(id = R.string.deteksi_penyakit),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Medium
            ),
        )
        Text(
            text = stringResource(id = R.string.gallery_or_camera),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
        )

    }

}
