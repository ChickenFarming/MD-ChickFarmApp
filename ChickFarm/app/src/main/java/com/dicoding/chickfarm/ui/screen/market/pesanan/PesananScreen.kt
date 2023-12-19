package com.dicoding.chickfarm.ui.screen.market.pesanan

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.chickfarm.ViewModelFactory
import com.dicoding.chickfarm.data.Produk
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.retrofit.ApiConfig
import com.dicoding.chickfarm.di.Injection

@Composable
fun PesananScreen(
    modifier: Modifier = Modifier,
    context: Context,
) {
    val viewModel: PesananViewModel =
        viewModel(factory = ViewModelFactory(Repository(ApiConfig().getApiService()), context))
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("login_status", Context.MODE_PRIVATE)
    val isLoggedIn =
        remember { mutableStateOf(sharedPreferences.getInt("id_user", -0)) }
    viewModel.setId(isLoggedIn.value)
    val groupedOrders by viewModel.groupedOrders.collectAsState()
    val refreshScreen by viewModel.refreshScreen.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LaunchedEffect(refreshScreen) {
            if (refreshScreen) {
                // Jika refreshScreen bernilai true, maka refresh layar
                viewModel.onScreenRefreshed()
            }
        }
        if (groupedOrders.isEmpty()) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Pesanan Kosong!!")
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            groupedOrders.forEach { (init, data) ->
                items(data, key = { it.idPesanan }) { data ->

                    OrdersListItemGrid(
                        productId = data.idProduk,
                        namaPenerima = data.namaPenerima,
                        alamat = data.alamatPengiriman,
                        metodePembayaran = data.metodePembayaran,
                        context = context,
                        orderId = data.idPesanan
                    )
                }

            }
        }

    }


}

@Composable
fun OrdersListItemGrid(
    orderId: Int,
    modifier: Modifier = Modifier,
    productId: Int,
    namaPenerima: String,
    alamat: String,
    metodePembayaran: String,
    context: Context
) {
    var showDialog by remember { mutableStateOf(false) }
    var productState by remember { mutableStateOf<List<Produk>>(emptyList()) }


    val viewModel: PesananViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(ApiConfig().getApiService()),
            context
        )
    )

    LaunchedEffect(productId) {
        // Menggunakan viewModel untuk mendapatkan data produk berdasarkan ID
        productState = viewModel.getProductById(productId)

    }


    Box(
        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 10.dp)

            .clip(RoundedCornerShape(5.dp))

            .clickable {
                showDialog = true
            }
            .background(MaterialTheme.colorScheme.surface),


        ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                productState.forEach { data ->

                    AsyncImage(
                        model = data.gambarProduk, contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .height(160.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))

                    )
                    Text(
                        text = data.namaProduk,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Rp. ${data.hargaProduk}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = modifier.padding(10.dp)
                    )

                    // AlertDialog
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                // Menutup dialog ketika di luar dialog ditekan
                                showDialog = false
                            },
                            title = {


                                Text(
                                    text = "Pesanan",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )


                            },

                            text = {
                                Column {
                                    AsyncImage(
                                        model = data.gambarProduk, contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .height(300.dp)
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(16.dp))

                                    )
                                    Text(
                                        text = data.namaProduk,
                                        modifier = Modifier.padding(5.dp),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                    )
                                    Text(
                                        text = "Rp. ${data.hargaProduk}",
                                        modifier = Modifier.padding(5.dp),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 15.sp,
                                        ),
                                    )
                                    Text(
                                        text = "Metode Pembayaran: $metodePembayaran",
                                        modifier = Modifier.padding(5.dp),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 15.sp,
                                        ),
                                    )
                                    Spacer(modifier = Modifier.height(15.dp))
                                }
                            },
                            confirmButton = {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Row() {

                                        Button(modifier = modifier.width(100.dp),
                                            onClick = {
                                                showDialog = false
                                            },
                                            colors = ButtonDefaults.buttonColors(Color.Black)
                                        ) {
                                            Text(
                                                "Batal",
                                                style = MaterialTheme.typography.bodyLarge.copy(
                                                    fontSize = 15.sp
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Button(modifier = modifier.width(100.dp),
                                            onClick = {
                                                showDialog = false
                                                viewModel.hapusPesanan(idPesanan = orderId)
                                                Toast.makeText(
                                                    context,
                                                    "Pesanan Dihapus",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        ) {
                                            Text(
                                                "Hapus",
                                                style = MaterialTheme.typography.bodyLarge.copy(
                                                    fontSize = 15.sp,
                                                )
                                            )
                                        }
                                    }
                                }
                            },


                            )

                    }


                }
            }


        }
    }


}



