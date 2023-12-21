package com.dicoding.chickfarm.ui.screen.market

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.Produk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MarketViewModel(private val repository: Repository) : ViewModel() {

    private val _groupedProduct = MutableStateFlow<Map<Char, List<Produk>>>(emptyMap())

    val groupedProduct: MutableStateFlow<Map<Char, List<Produk>>> get() = _groupedProduct
    init {
        loadGroupedProducts()
    }

    private fun loadGroupedProducts() {
        viewModelScope.launch {
            try {
                val allProducts = repository.getAllProduct() // Memanggil di dalam coroutine
                val groupedProducts = allProducts
                    .sortedBy { it.namaProduk }
                    .groupBy { it.namaProduk[0] }
                _groupedProduct.value = groupedProducts
            } catch (e: Exception) {
                // Handle error, if any
                e.printStackTrace()
            }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    fun search(newQuery: String) {
        viewModelScope.launch {
            try {
                _query.value = newQuery
                val searchResult = repository.searchProduct(newQuery)
                    .sortedBy { it.namaProduk }
                    .groupBy { it.namaProduk[0] }
                _groupedProduct.value = searchResult
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun setQuery(searchValue: String) {
        viewModelScope.launch {
            try {
                val searchResult = repository.searchProduct(searchValue)
                    .sortedBy { it.namaProduk }
                    .groupBy { it.namaProduk[0] }
                _groupedProduct.value = searchResult
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        Log.d("gadgawg", searchValue)

    }
    private val _searchValue = mutableStateOf("")
    val searchValue: State<String> get() = _searchValue
    fun setSearhValue(searchValue: String){
        _searchValue.value = searchValue
    }

}