package com.dicoding.chickfarm.ui.screen.market.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.Produk
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val repository: Repository, private val context: Context
) : ViewModel() {

    suspend fun getProductById(productId: Int): List<Produk> {
        return repository.getProductById(productId)
    }


    fun insertPesanan(
        idUser: Int,
        namaPenerima: String,
        idProduk: Int,
        namaProduk: String,
        alamatPengiriman: String,
    ) {
        viewModelScope.launch {
            try {
                repository.insertPesanan(
                    idUser = idUser,
                    namaPenerima = namaPenerima,
                    idProduk = idProduk,
                    namaProduk = namaProduk,
                    alamatPengiriman = alamatPengiriman,
                )

                showToast("Produk dipesan!! cek di menu Produk Saya")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        showToast("Produk dipesan!!, Cek di menu Pesanan Saya")
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }


}