package com.dicoding.chickfarm.ui.screen.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dicoding.chickfarm.R
import com.dicoding.chickfarm.navigation.Screen
import com.dicoding.chickfarm.ui.screen.diseasedetector.DiseaseDetectorHeadline
import com.dicoding.chickfarm.ui.screen.diseasedetector.TakeImageActivity
import com.google.android.gms.maps.model.LatLng


@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    context: Context,
    navigateToMap: () -> Unit


) {
    LazyColumn(modifier.padding(30.dp)) {
        item {
        Text(
            text = stringResource(id = R.string.selamat_datang),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            ),
        )
        Spacer(modifier = modifier.height(20.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        FirstFeature(navController = navController)
        Spacer(modifier = modifier.height(30.dp))
        MapFeature(navigateToMap = navigateToMap)
    }
        }
    }

}


@Composable
fun FirstFeature(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 20.dp)
            .width(400.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.market_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
//                .clip(Shapes.medium)
        )
        Column {
            Text(
                text = stringResource(id = R.string.kebutuhan_ternak_ayam),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium
                ),
            )
            Text(
                text = stringResource(id = R.string.berkualitas),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ),
            )
            Text(
                text = stringResource(id = R.string.terpercaya),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ),
            )
            Spacer(modifier = modifier.height(10.dp))
            Button(
                onClick = {
                  navController.navigate(Screen.Market.route) {
                      // Pastikan tidak ada instruksi untuk menyimpan MarketScreen di dalam back stack
                      popUpTo(navController.graph.findStartDestination().id) {
                          saveState = true
                      }
                      restoreState = true
                  }
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp)
                ,
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.menu_market),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize =15.sp
                    ),
                )
            }
        }

    }
}

@Composable
fun MapFeature(
    modifier: Modifier = Modifier,
    navigateToMap: () -> Unit
) {
   Column {
       Text(
           text = stringResource(id = R.string.lokasi_penjual),
           style = MaterialTheme.typography.titleLarge.copy(
               fontWeight = FontWeight.Bold,
               fontSize = 17.sp
           ),
       )
       Column(
           modifier = modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Image(
               painter = painterResource(R.drawable.map),
               contentDescription = null,
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .fillMaxWidth()
                   .height(250.dp)
                   .padding(vertical = 20.dp)
                .clip(shape = RoundedCornerShape(16.dp))
           )

           Button(
               onClick = {
                   navigateToMap()
               },
               modifier = Modifier
                   .width(100.dp)
                   .height(40.dp)
               ,
               shape = RoundedCornerShape(5.dp),
               colors = ButtonDefaults.buttonColors(Color(0xFFC95050))
           ) {
               Text(
                   text = stringResource(id = R.string.maps_menu),
                   style = MaterialTheme.typography.titleSmall.copy(
                       fontWeight = FontWeight.Bold,
                       fontSize =15.sp
                   ),
               )
           }
       }
   }
}


