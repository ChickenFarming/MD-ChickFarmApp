package com.dicoding.chickfarm.ui.screen.market.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.dicoding.chickfarm.data.Produk
import com.dicoding.chickfarm.data.response.DataProduct
import com.dicoding.chickfarm.data.retrofit.ApiConfig
import com.dicoding.chickfarm.di.Injection
import com.dicoding.chickfarm.ui.common.UiState


@Composable
fun DetailProductScreen(
    productId: Int,
    context: Context,

) {
    var productState by remember { mutableStateOf<List<Produk>>(emptyList()) }


    val viewModel: DetailProductViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(ApiConfig().getApiService()),
            context
        )
    )

    LaunchedEffect(productId) {
        // Menggunakan viewModel untuk mendapatkan data produk berdasarkan ID
        productState = viewModel.getProductById(productId)
    }

    productState.forEach { selectedProduct ->
        DetailContent(
            shopName = "daagw",
            image = selectedProduct.gambarProduk ?: "",
            productName = selectedProduct.namaProduk ?: "",
            price = selectedProduct.hargaProduk ?: 0,
            desc = selectedProduct.deskripsiProduk ?: ""
        )
    }
}

@Composable
fun DetailContent(
    image: String,
    shopName: String,
    productName: String,
    desc: String,
    price:Int,

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
            text = price.toString(),
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




