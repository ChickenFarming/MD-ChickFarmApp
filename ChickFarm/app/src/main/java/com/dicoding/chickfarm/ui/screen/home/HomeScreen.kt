package com.dicoding.chickfarm.ui.screen.home

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dicoding.chickfarm.R
import com.dicoding.chickfarm.navigation.Screen


@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    context: Context,
    navigateToMap: () -> Unit


) {
    Column(modifier.padding(30.dp)) {

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

       }
   }
}


