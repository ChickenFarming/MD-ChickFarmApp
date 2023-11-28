package com.dicoding.chickfarm.ui.screen.utils

import android.content.Context

object Utils{

fun setLoginStatus(context: Context, isLoggedIn: Boolean) {
    val sharedPreferences = context.getSharedPreferences("login_status", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("is_logged_in", isLoggedIn)
    editor.apply()
}
}
