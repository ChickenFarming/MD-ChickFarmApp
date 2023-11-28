package com.dicoding.chickfarm.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Camera : Screen("camera")
    object Market : Screen("market")
    object Maps : Screen("maps")
    object Login: Screen("login")
    object Signup: Screen("signup")

}