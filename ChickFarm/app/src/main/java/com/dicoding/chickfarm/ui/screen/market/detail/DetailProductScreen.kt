package com.dicoding.chickfarm.ui.screen.market.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.chickfarm.ViewModelFactory
import com.dicoding.chickfarm.data.Produk
import com.dicoding.chickfarm.data.retrofit.ApiConfig
import com.dicoding.chickfarm.di.Injection
import kotlinx.coroutines.launch

import androidx.lifecycle.lifecycleScope

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
            image = selectedProduct.gambarProduk ?: "",
            productName = selectedProduct.namaProduk ?: "",
            price = selectedProduct.hargaProduk ?: 0,
            desc = selectedProduct.deskripsiProduk ?: "",
            idProduk = selectedProduct.idProduk,
            viewModel = viewModel,
            context = context
        )
    }
}

@Composable
fun DetailContent(
    image: String,
    productName: String,
    desc: String,
    price: Int,
    idProduk: Int,
    viewModel: DetailProductViewModel,
    context: Context

) {

    var showDialog by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Konten di atas
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .size(300.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(
                text = productName,
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = "Rp. ${price.toString()}",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 15.sp,
                ),
            )
            Text(
                text = desc,
                modifier = Modifier
                    .padding(5.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 15.sp,
                ),
            )
        }

        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onBackground)
        ) {
            Text(
                "Beli Sekarang",
                color = MaterialTheme.colorScheme.background
            )
        }
    }


    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val sharedPreferences =
        LocalContext.current.getSharedPreferences("login_status", Context.MODE_PRIVATE)
    val isLoggedIn =
        remember { mutableStateOf(sharedPreferences.getInt("id_user", -0)) }

    // AlertDialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Menutup dialog ketika di luar dialog ditekan
                showDialog = false
            },
            title = {
                Text(
                    text = "Masukkan data pesanan",
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
                    Text(
                        text = productName,
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Text(
                        text = "Rp. ${price.toString()}",
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 15.sp,
                        ),
                    )
                    Text(
                        text = "Metode Pembayaran : COD",
                        modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 15.sp,
                        ),
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    // TextFields untuk nama dan alamat
                    TextField(
                        value = name,
                        onValueChange = { newName -> name = newName },
                        label = { Text("Nama Penerima") }
                    )
                    TextField(
                        value = address,
                        onValueChange = { newAddress -> address = newAddress },
                        label = { Text("Alamat Penerima") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {

                        scope.launch {
                            if (name.isNotEmpty() && address.isNotEmpty()) {
                                try {
                                    viewModel.insertPesanan(
                                        idUser = isLoggedIn.value,
                                        namaPenerima = name,
                                        idProduk = idProduk,
                                        namaProduk = productName,
                                        alamatPengiriman = address,
                                    )
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                showDialog = false
                            } else {
                                Toast.makeText(context, "lengkapi semua nya", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                // Tombol untuk menutup dialog
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Batal")
                }
            }
        )

    }


}




