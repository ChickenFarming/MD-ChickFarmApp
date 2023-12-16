package com.dicoding.chickfarm.data

import com.dicoding.chickfarm.data.response.AkunUserResponse
import com.dicoding.chickfarm.data.response.DataItem
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