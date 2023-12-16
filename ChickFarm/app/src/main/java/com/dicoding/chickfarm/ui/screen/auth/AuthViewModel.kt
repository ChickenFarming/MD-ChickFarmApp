package com.dicoding.chickfarm.ui.screen.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.response.AkunUserResponse
import com.dicoding.chickfarm.data.response.DataItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthViewModel(private val repository: Repository, private val context: Context) : ViewModel() {
    //    fun isValidCredentials(email: String, password: String): Boolean {
//        // Implementasikan logika validasi kredensial Anda di sini
//        // Contoh sederhana: Email harus "user@example.com" dan password harus "password123"
//        return email == "fajar" && password == "123"
//    }


    suspend fun getAllUsers(): AkunUserResponse {
        return repository.getAllUsers()
    }

//     Fungsi untuk memanggil insertUser dari repository
    fun insertUser(email: String, username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.insertUser(email, username, password)

                // Lakukan sesuatu dengan response jika diperlukan
            } catch (e: Exception) {
                // Tangani kesalahan, misalnya tampilkan pesan ke pengguna
                e.printStackTrace()
            }
        }
     showToast("Registrasi Berhasil")
    }
    private fun showToast(text:String){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

//    suspend fun insertUser(email:String, username:String, password:String){
//        repository.insertUser(email=email, username=username,password = password)
//    }


//    suspend fun insertUser(email: String, username: String, password: String): String {
//        return suspendCancellableCoroutine { continuation ->
//            viewModelScope.launch {
//                try {
//                    val response = repository.insertUser(email, username, password)
////                    if (response.message == "CREATE new user Success") {
//                    continuation.resume("Registrasi Berhasil")
////                    } else {
////                        // Tangani situasi lain jika diperlukan
////                    }
//                    // Lakukan sesuatu dengan response jika diperlukan
//                } catch (e: Exception) {
//                    // Tangani kesalahan, misalnya tampilkan pesan ke pengguna
//                    e.printStackTrace()
//                    continuation.resumeWithException(e)
//                }
//            }
//        }
//    }


}