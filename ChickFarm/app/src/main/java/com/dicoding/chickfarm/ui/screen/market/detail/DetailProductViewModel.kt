package com.dicoding.chickfarm.ui.screen.market.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.Produk
import com.dicoding.chickfarm.data.response.DataProduct
import com.dicoding.chickfarm.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val repository: Repository, private val context: Context
): ViewModel() {
    
//    private val _uiState: MutableStateFlow<UiState<Produk>> =
//        MutableStateFlow(UiState.Loading)
//    val uiState :StateFlow<UiState<Produk>>
//        get() = _uiState
//
//    fun getProductById(productId:Int){
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            _uiState.value = UiState.Success(repository.getProductyId(productId))
//        }
//    }
suspend fun getProductById(productId: Int): List<Produk> {
        return repository.getProductById(productId)
}


    fun insertPesanan(
        idUser : Int,
        namaPenerima:String,
        idProduk:Int,
        namaProduk:String,
        alamatPengiriman:String,
    ) {
        viewModelScope.launch {
            try {
            repository.insertPesanan(
                    idUser =  idUser,
                    namaPenerima = namaPenerima,
                    idProduk = idProduk,
                    namaProduk = namaProduk,
                    alamatPengiriman = alamatPengiriman,
                )

                showToast("Produk dipesan!! cek di menu Produk Saya")
            } catch (e: Exception) {
                // Tangani kesalahan, misalnya tampilkan pesan ke pengguna
                e.printStackTrace()
            }
        }
//        showToast("Registrasi Berhasil")
    }

    private fun showToast(text:String){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }


}