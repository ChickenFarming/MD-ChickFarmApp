package com.dicoding.chickfarm.ui.screen.market

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dicoding.chickfarm.Repository
import com.dicoding.chickfarm.data.Produk
import kotlinx.coroutines.flow.MutableStateFlow

class MarketViewModel(private val repository: Repository) : ViewModel() {

    private val _groupedProduct = MutableStateFlow(
        repository.getAllProduct()
            .sortedBy { it.namaProduk }
            .groupBy { it.namaProduk[0] }
    )
    val groupedProduct: MutableStateFlow<Map<Char, List<Produk>>> get() = _groupedProduct


}