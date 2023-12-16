package com.dicoding.chickfarm.ui.screen.market

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.Produk
import com.dicoding.chickfarm.data.response.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MarketViewModel(private val repository: Repository) : ViewModel() {

//    private val _groupedProduct = MutableLiveData<Map<Char, List<Produk>>>()
private val _groupedProduct = MutableStateFlow<Map<Char, List<Produk>>>(emptyMap())
//    private val _groupedProduct = MutableStateFlow(
//        repository.getAllProduct()
//            .sortedBy { it.namaProduk }
//            .groupBy { it.namaProduk[0] }
//    )
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
//    fun search(newQuery: String) {
//        _query.value = newQuery
//        _groupedProduct.value = repository.searchProduct(_query.value)
//            .sortedBy { it.namaProduk }
//            .groupBy { it.namaProduk[0] }
//    }

    fun search(newQuery: String) {
        viewModelScope.launch {
            try {
                _query.value = newQuery
                val searchResult = repository.searchProduct(newQuery)
                    .sortedBy { it.namaProduk }
                    .groupBy { it.namaProduk[0] }
                _groupedProduct.value = searchResult
            } catch (e: Exception) {
                // Handle error, if any
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
                // Handle error, if any
                e.printStackTrace()
            }
        }
    }


//    fun setQuery(searchValue:String){
//        _query.value = searchValue
//        _groupedProduct.value = repository.searchProduct(_query.value)
//            .sortedBy { it.namaProduk }
//            .groupBy { it.namaProduk[0] }
//    }

//    val searchValue = mutableStateOf("jajaja")
    private val _searchValue = MutableLiveData<String>()
    val searchValue: LiveData<String>
        get() = _searchValue
//    fun setSearchValue(value :String){
//        _searchValue.value = value
//
//        Log.d("hahahhaha", "${searchValue.value}")
//    }


}