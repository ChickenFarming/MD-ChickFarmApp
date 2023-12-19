package com.dicoding.chickfarm.ui.screen.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.response.AkunUserResponse
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository, private val context: Context) : ViewModel() {

    suspend fun getAllUsers(): AkunUserResponse {
        return repository.getAllUsers()
    }

//     Fungsi untuk memanggil insertUser dari repository
    fun insertUser(email: String, username: String, password: String) {
        viewModelScope.launch {
            try {

                repository.insertUser(email, username, password)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
     showToast("Registrasi Berhasil")
    }
    private fun showToast(text:String){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }



}