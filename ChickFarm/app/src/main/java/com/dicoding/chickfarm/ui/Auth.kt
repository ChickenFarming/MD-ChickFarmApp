package com.dicoding.chickfarm.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dicoding.chickfarm.MainActivity
import com.dicoding.chickfarm.ui.screen.map.MapViewModel
import com.dicoding.chickfarm.R
import com.dicoding.chickfarm.navigation.NavigationItem
import com.dicoding.chickfarm.navigation.Screen
import com.dicoding.chickfarm.ui.screen.CameraScreen
import com.dicoding.chickfarm.ui.screen.HomeScreen
import com.dicoding.chickfarm.ui.screen.map.MapScreen
import com.dicoding.chickfarm.ui.screen.MarketScreen
import com.dicoding.chickfarm.ui.screen.auth.LoginScreen
import com.dicoding.chickfarm.ui.screen.auth.RegistrationScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Auth(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    context: Context
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sharedPreferences =
        LocalContext.current.getSharedPreferences("login_status", Context.MODE_PRIVATE)
    val isLoggedIn =
        remember { mutableStateOf(sharedPreferences.getBoolean("is_logged_in", false)) }

    if (isLoggedIn.value) {
        // Jika sudah login, langsung arahkan ke MainActivity atau halaman setelah login
        val intent = Intent(LocalContext.current, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        LocalContext.current.startActivity(intent)
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        Icon(
                            modifier = Modifier.padding(12.dp),
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.app_name)
                        )
                    },
                    title = {
                        when (currentRoute) {
                            Screen.Login.route -> Text(stringResource(id = R.string.login))
                            else -> Text(stringResource(R.string.menu_market))
                        }
                    }
                )

            },
            modifier = modifier
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = Screen.Login.route,
            ) {
                composable(Screen.Login.route) {
                    LoginScreen(navController = navController, context = context)
                }
                composable(Screen.Signup.route) {
                    RegistrationScreen(navController = navController)
                }

            }


        }
    }


}


