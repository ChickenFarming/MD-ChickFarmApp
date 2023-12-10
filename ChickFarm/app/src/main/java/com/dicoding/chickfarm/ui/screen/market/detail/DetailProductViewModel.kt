package com.dicoding.chickfarm.ui.screen.market.detail

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chickfarm.Repository
import com.dicoding.chickfarm.data.ListProduk
import com.dicoding.chickfarm.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val repository: Repository
): ViewModel() {
    
    private val _uiState: MutableStateFlow<UiState<ListProduk>> =
        MutableStateFlow(UiState.Loading)
    val uiState :StateFlow<UiState<ListProduk>>
        get() = _uiState

    fun getProductById(productId:Long){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getProductyId(productId))
        }
    }
}