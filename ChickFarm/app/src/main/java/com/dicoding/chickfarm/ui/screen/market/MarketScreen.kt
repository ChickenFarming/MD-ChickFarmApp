package com.dicoding.chickfarm.ui.screen.market

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.chickfarm.Repository
import com.dicoding.chickfarm.ViewModelFactory
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun MarketScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MarketViewModel = viewModel(factory = ViewModelFactory(Repository()))

    val groupedProduct by viewModel.groupedProduct.collectAsState()
    Column(modifier = modifier) {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
                .testTag("MusikList"), state = listState
        ) {
            groupedProduct.forEach { (init, data) ->
                items(data, key = { it.id }) { data ->
                    ProductListItem(
                        namaProduct = data.namaProduk, photoUrl = data.photoUrl,
                        productId = data.id, namaToko = data.namaToko, harga = data.harga
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

        }

    }
}

@Composable
fun ProductListItem(
    namaProduct: String,
    photoUrl: String,
//    navigateToDetail: (Long) -> Unit,
    productId: Long,
    namaToko: String,
    harga: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
//                navigateToDetail(musikId)
            }
            .shadow(elevation = 1.dp)

    ) {
        AsyncImage(
            model = photoUrl, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .height(120.dp)
                .width(160.dp)
                .clip(RoundedCornerShape(16.dp))

        )
        Column(
            modifier = Modifier.height(100.dp).fillMaxWidth().padding(start = 10.dp)
        ) {

            Text(
                text = namaToko,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
,                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray
                ),
            )
            Text(
                text = namaProduct,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
,                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray
                ),
            )
            Text(
                text = harga,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .weight(1f),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray
                ),
            )

        }
    }
}
