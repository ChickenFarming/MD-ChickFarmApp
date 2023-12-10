package com.dicoding.chickfarm.ui.screen.market.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.chickfarm.ViewModelFactory
import com.dicoding.chickfarm.di.Injection
import com.dicoding.chickfarm.ui.common.UiState


@Composable
fun DetailProductScreen(
    productId: Long,
    viewModel: DetailProductViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getProductById(productId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                     shopName = data.produk.namaToko,
                    image = data.produk.image,
                    productName = data.produk.namaProduk,
                    price = data.produk.harga,
                    desc = data.produk.desc
                )
            }
            is UiState.Error -> {}
        }

    }

}

@Composable
fun DetailContent(
    image: Int,
    shopName: String,
    productName: String,
    desc: String,
    price:String,

    ) {
//    val orderCount by rememberSaveable { mutableStateOf(count) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)

    ) {
        AsyncImage(
            model = image, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .size(300.dp)
                .clip(RoundedCornerShape(10.dp))

        )
        Text(
            text = shopName,
            modifier = Modifier
                .padding(5.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                ),
        )
        Text(
            text = productName,
            modifier = Modifier
                .padding(5.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,

                ),
        )
        Text(
            text = price,
            modifier = Modifier
                .padding(5.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 15.sp,
            ),
        )
        Text(
            text = desc,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 15.sp,
            ),
        )

    }
}




