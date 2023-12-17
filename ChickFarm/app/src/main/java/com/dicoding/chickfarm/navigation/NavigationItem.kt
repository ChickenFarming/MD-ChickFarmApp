package com.dicoding.chickfarm.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

sealed class Screen(val route: String) {
//    Main
    object Home : Screen("home")
    object DiseaseDetector: Screen("disease")
    object Market : Screen("market")
    object DetailProduct: Screen("market/{productId}"){
        fun createRoute(productId: Int) = "market/$productId"
    }
    object Pesanan :Screen("pesanan")


//Auth
    object Maps : Screen("maps")
    object Login: Screen("login")
    object Signup: Screen("signup")

}