package com.dicoding.chickfarm.ui.common

import com.dicoding.chickfarm.data.Produk

sealed class UiState<out T: Any?> {

    object Loading : UiState<Nothing>()

    data class Success<out T: Any>(val data: Produk) : UiState<T>()

    data class Error(val errorMessage: String) : UiState<Nothing>()
}