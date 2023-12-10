package com.dicoding.chickfarm.navigation

import android.net.Uri
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

sealed class Screen(val route: String) {
//    Main
    object Home : Screen("home")
    object Camera : Screen("camera")
    object DiseaseDetector: Screen("disease")
    object Market : Screen("market")
    object DetailProduct: Screen("market/{productId}"){
        fun createRoute(productId: Long) = "market/$productId"
    }


//Auth
    object Maps : Screen("maps")
    object Login: Screen("login")
    object Signup: Screen("signup")

}