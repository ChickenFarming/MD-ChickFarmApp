package com.dicoding.chickfarm.ui.screen.auth

import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    fun isValidCredentials(email: String, password: String): Boolean {
        // Implementasikan logika validasi kredensial Anda di sini
        // Contoh sederhana: Email harus "user@example.com" dan password harus "password123"
        return email == "fajar" && password == "123"
    }
}