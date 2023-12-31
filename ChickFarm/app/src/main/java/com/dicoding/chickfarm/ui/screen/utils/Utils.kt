package com.dicoding.chickfarm.ui.screen.utils

import android.content.Context

import java.io.File

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun setLoginStatus(context: Context, isLoggedIn: Boolean,idUser :Int?) {
        val sharedPreferences = context.getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        if (idUser != null) {
            editor.putInt("id_user", idUser)
        }
        editor.apply()
    }
    private val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

    fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        return File.createTempFile(timeStamp, ".jpg", filesDir)
    }

}




