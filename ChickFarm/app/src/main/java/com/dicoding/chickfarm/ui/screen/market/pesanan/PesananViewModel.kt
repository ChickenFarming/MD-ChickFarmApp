package com.dicoding.chickfarm.ui.screen.market.pesanan


import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chickfarm.MainActivity
import com.dicoding.chickfarm.data.Pesanan
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.Produk
import com.dicoding.chickfarm.data.response.PesananResponse
import com.dicoding.chickfarm.data.response.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PesananViewModel(private val repository: Repository) : ViewModel() {

    private val idUser = mutableStateOf(0)
    fun setId(newId: Int) {
        idUser.value = newId
    }

    private val _groupedOrders = MutableStateFlow<Map<Char, List<Pesanan>>>(emptyMap())

    val groupedOrders: MutableStateFlow<Map<Char, List<Pesanan>>> get() = _groupedOrders
    init {
        loadGroupedOrders()
    }

    private fun loadGroupedOrders() {
        viewModelScope.launch {
            try {

                val allOrders = repository.getAllOrders() // Memanggil di dalam coroutine
                val groupedOrders = allOrders.filter {it.idUser == idUser.value }
                    .sortedBy { it.namaProduk }
                    .groupBy { it.namaProduk[0] }
                _groupedOrders.value = groupedOrders
            } catch (e: Exception) {
                // Handle error, if any
                e.printStackTrace()
            }
        }
    }

    suspend fun getProductById(productId: Int): List<Produk> {
        return repository.getProductById(productId)
    }

    private val _refreshScreen = MutableStateFlow<Boolean>(false)
    val refreshScreen: MutableStateFlow<Boolean> get() = _refreshScreen

    fun hapusPesanan(idPesanan: Int) {
        viewModelScope.launch {
            repository.hapusPesanan(idPesanan)
            _refreshScreen.value = true
        loadGroupedOrders()
        }
    }

    fun onScreenRefreshed() {
        _refreshScreen.value = false // Mengembalikan state refreshScreen ke nilai awal
    }

}