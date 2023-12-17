package com.dicoding.chickfarm.data

import android.util.Log
import com.dicoding.chickfarm.data.response.AkunUserResponse
import com.dicoding.chickfarm.data.response.DataItem
import com.dicoding.chickfarm.data.response.DataPesanan
import com.dicoding.chickfarm.data.response.PesananResponse
import com.dicoding.chickfarm.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val apiService: ApiService
) {
    // Fungsi untuk mendapatkan semua produk dari API
    suspend fun getAllProduct(): List<Produk> {
        return try {
            val response = apiService.getAllProduct()
            response.data?.mapNotNull { it?.toProduk() } ?: emptyList()
        } catch (e: Exception) {
            // Handle error sesuai kebutuhan aplikasi Anda
            emptyList()
        }
    }

    suspend fun getProductById(productId: Int): List<Produk> {
        return getAllProduct().filter { it.idProduk == productId }
    }

    suspend fun searchProduct(query: String): List<Produk> {
        return getAllProduct().filter {
            it.namaProduk.contains(query, ignoreCase = true)
        }

    }


//    Api

//    Auth
suspend fun getAllUsers(): AkunUserResponse {
    return apiService.getAllUsers()
}

suspend fun insertUser(email: String, username: String, password: String): AkunUserResponse {
    return withContext(Dispatchers.IO) {
        try {
            return@withContext apiService.insertUser(DataItem(idUser = 0,email= email, username = username, password = password))
        } catch (e: Exception) {
            throw e
        }
    }
}


//    Pesanan
    suspend fun insertPesanan(
        idUser : Int,
        namaPenerima:String,
        idProduk:Int,
        namaProduk:String,
        alamatPengiriman:String,
    ): PesananResponse {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext apiService.insertPesanan(DataPesanan(
                    idPesanan = 0,
                    idUser =  idUser,
                    namaPenerima = namaPenerima,
                    idProduk = idProduk,
                    namaProduk = namaProduk,
                    alamatPengiriman = alamatPengiriman,
                    metodePembayaran = "COD"
                ))
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getAllOrders(): List<Pesanan> {
        return try {
            val response = apiService.getAllOrders()
            response.data?.mapNotNull { it?.toPesanan() } ?: emptyList()
        } catch (e: Exception) {
            // Handle error sesuai kebutuhan aplikasi Anda
            emptyList()
        }
    }

    suspend fun hapusPesanan(idPesanan: Int) {
        apiService.hapusPesanan(idPesanan)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                Repository(apiService).apply {
                    instance = this
                }
            }
    }
}