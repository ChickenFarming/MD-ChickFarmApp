package com.dicoding.chickfarm.ui.screen.market.detail

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
    private val repository: Repository
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
}