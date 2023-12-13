package com.dicoding.chickfarm.ui.screen.market

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.chickfarm.Repository
import com.dicoding.chickfarm.ViewModelFactory
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.dicoding.chickfarm.R

@Composable
fun MarketScreen(
    modifier: Modifier = Modifier,
    navigateToPayment: (Long) -> Unit,
) {
    val viewModel: MarketViewModel = viewModel(factory = ViewModelFactory(Repository()))

    val groupedProduct by viewModel.groupedProduct.collectAsState()
        val listState = rememberLazyListState()
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        MarketHeadline()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxWidth()
//            modifier = modifier.testTag("RewardList")
        ) {

            groupedProduct.forEach { (init, data) ->

                items(data, key = { it.id }) { data ->
                    ProductListItem(
                        namaProduct = data.namaProduk, image = data.image,
                        productId = data.id, namaToko = data.namaToko, harga = data.harga,
                        navigateToPayment = navigateToPayment, lokasi = data.lokasi
                    )
                }
            }
        }
//        }


//        LazyColumn(
//            modifier = Modifier
//                .padding(top = 10.dp)
//                .testTag("MusikList"), state = listState
//        ) {
//            groupedProduct.forEach { (init, data) ->
//                items(data, key = { it.id }) { data ->
//                    ProductListItem(
//                        namaProduct = data.namaProduk, photoUrl = data.photoUrl,
//                        productId = data.id, namaToko = data.namaToko, harga = data.harga,
//                        navigateToPayment = navigateToPayment
//                    )
//                    Spacer(modifier = Modifier.height(10.dp))
//                }
//            }
//
        }


}

@Composable
fun ProductListItem(
    modifier: Modifier = Modifier,
    namaProduct: String,
    image: Int,
//    navigateToDetail: (Long) -> Unit,
    productId: Long,
    namaToko: String,
    harga: String,
    lokasi:String,
    navigateToPayment: (Long) -> Unit
) {

    Box(
        modifier = Modifier
            .height(270.dp)
//            .fillMaxWidth()
//            .padding(horizontal = 10.dp)
            .border(
                width = 1.dp, // Lebar border
                color = MaterialTheme.colorScheme.onBackground, // Warna border
                shape = RoundedCornerShape(10.dp) // Bentuk sudut yang dibulatkan
            )
            .clip(RoundedCornerShape(15.dp))
//            .clip(RoundedCornerShape(15.dp))
//            .clip(RoundedCornerShape(15.dp)
//            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                navigateToPayment(productId)
            },


    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(){
            Text(
                text = namaToko,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Normal
                )
            )
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(170.dp)
//                .clip(Shapes.medium)
                )
                Text(
                    text = namaProduct,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                )
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = lokasi,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Text(
                    text = harga,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Normal
                    ),
                )
            }
        }
    }


//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 10.dp)
//            .clip(RoundedCornerShape(15.dp))
//            .background(MaterialTheme.colorScheme.surface)
//            .clickable {
//                navigateToPayment(productId)
//            }
//            .shadow(elevation = 1.dp)
//
//    ) {
//        AsyncImage(
//            model = photoUrl, contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .padding(8.dp)
//                .height(120.dp)
//                .width(160.dp)
//                .clip(RoundedCornerShape(16.dp))
//
//        )
//        Column(
//            modifier = Modifier.height(100.dp).fillMaxWidth().padding(start = 10.dp)
//        ) {
//
//            Text(
//                text = namaToko,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//,                style = MaterialTheme.typography.bodyLarge.copy(
//                    color = Color.Gray
//                ),
//            )
//            Text(
//                text = namaProduct,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//,                style = MaterialTheme.typography.bodyLarge.copy(
//                    color = Color.Gray
//                ),
//            )
//            Text(
//                text = harga,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier
//                    .weight(1f),
//                style = MaterialTheme.typography.bodyLarge.copy(
//                    color = Color.Gray
//                ),
//            )
//
//        }
//    }
}

@Composable
fun MarketHeadline(
    modifier: Modifier = Modifier
){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding( vertical = 20.dp)
            .width(270.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
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
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = stringResource(id = R.string.terpercaya),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
        }
        Image(
            painter = painterResource(R.drawable.market_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
//                .clip(Shapes.medium)
        )
    }
}
