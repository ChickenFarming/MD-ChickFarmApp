package com.dicoding.chickfarm.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Map
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dicoding.chickfarm.AuthActivity
import com.dicoding.chickfarm.R
import com.dicoding.chickfarm.navigation.NavigationItem
import com.dicoding.chickfarm.navigation.Screen
import com.dicoding.chickfarm.ui.screen.CameraScreen
import com.dicoding.chickfarm.ui.screen.HomeScreen
import com.dicoding.chickfarm.ui.screen.map.MapScreen
import com.dicoding.chickfarm.ui.screen.MarketScreen
import com.dicoding.chickfarm.ui.screen.utils.Utils
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executor

data class MenuItem(val title: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChickFarmApp(

    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    context: Context,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

//    Item untuk navigationDrawer
    val items = listOf(
        MenuItem(
            title = stringResource(R.string.riwayat_menu),
            icon = Icons.Default.History
        ),
        MenuItem(
            title = stringResource(R.string.maps_menu),
            icon = Icons.Default.Map
        ),
        MenuItem(
            title = stringResource(R.string.logout),
            icon = Icons.Default.Logout
        ),
    )
    val selectedItem = remember { mutableStateOf(items[0]) }

    val sharedPreferences =
        LocalContext.current.getSharedPreferences("login_status", Context.MODE_PRIVATE)
    val isLoggedIn =
        remember { mutableStateOf(sharedPreferences.getBoolean("is_logged_in", false)) }

    if (!isLoggedIn.value) {
        // Jika sudah login, langsung arahkan ke MainActivity atau halaman setelah login
        val intent = Intent(LocalContext.current, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        LocalContext.current.startActivity(intent)
    } else {

        Scaffold(

            topBar = {
                when (currentRoute) {
                    Screen.Home.route -> {
                        MyTopBar(
                            onMenuClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }

                        )
                    }

                    Screen.Camera.route, Screen.Market.route, Screen.Maps.route -> {
                        TopAppBar(
                            navigationIcon = {
                                when (currentRoute) {
                                    Screen.Camera.route, Screen.Market.route -> {
                                        Icon(
                                            modifier = Modifier.padding(12.dp),
                                            imageVector = Icons.Default.Info,
                                            contentDescription = stringResource(R.string.app_name)
                                        )
                                    }

                                    Screen.Maps.route -> {

                                        IconButton(onClick = { }) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = stringResource(R.string.back_button)
                                            )

                                        }
                                    }

                                }
                            },
                            title = {
                                when (currentRoute) {
                                    Screen.Camera.route -> Text(stringResource(R.string.menu_camera))
                                    Screen.Maps.route -> Text(stringResource(id = R.string.maps_menu))
                                    else -> Text(stringResource(R.string.menu_market))
                                }
                            }
                        )
                    }
                    // Tambahkan kondisi lain jika diperlukan
                    else -> {
                        // Kondisi default jika tidak ada yang cocok
                    }
                }


            },
            bottomBar = {
//            if (currentRoute == Screen.Home.route || currentRoute == Screen.Camera.route || currentRoute == Screen.Market.route) {
//                if (drawerState.isClosed) {
                if (currentRoute != Screen.Maps.route) {
                    BottomBar(navController)

                }
//                }
//            }
            },
            modifier = modifier
        ) { innerPadding ->
            ModalNavigationDrawer(
                modifier = modifier.padding(innerPadding),
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(Modifier.height(12.dp))
                        items.forEach { item ->
                            NavigationDrawerItem(
                                icon = { Icon(item.icon, contentDescription = null) },
                                label = { Text(item.title) },
                                selected = item == selectedItem.value,
                                onClick = {
                                    selectedItem.value = item
                                    when (item.title) {
                                        "Maps" -> navController.navigate(Screen.Maps.route)
                                        "Logout" -> {
                                            val intent = Intent(context, AuthActivity::class.java)
                                            intent.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            context.startActivity(intent)
                                            Utils.setLoginStatus(context, false)

                                        }

                                    }

                                },
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                    }
                },
                gesturesEnabled = if (currentRoute == Screen.Maps.route) {
//                Jika berada di Map maka Navigation Drawer tidak bisa dibuka dengan digeser
                    drawerState.isOpen
                } else {
                    drawerState.isOpen || drawerState.isClosed
                },
                content = {

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,

                        ) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                            )
                        }
                        composable(Screen.Camera.route) {
                            CameraScreen(
                                outputDirectory = outputDirectory,
                                executor = executor,
                                onImageCaptured = onImageCaptured,
                                onError = { Log.e("kilo", "View error", it) },
                            )
                        }
                        composable(Screen.Market.route) {

                            MarketScreen()
//                        MapsActivity()
                        }
                        composable(Screen.Maps.route) {
                            LaunchedEffect(drawerState.isOpen) {
                                drawerState.close()
                            }
                            MapScreen(context = context)
//                        MapsActivity()
                        }

                    }
                }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.menu)
                )
            }
        },
        title = {
            Text(stringResource(R.string.app_name))
        },
    )
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_camera),
                icon = Icons.Default.CameraAlt,
                screen = Screen.Camera
            ),
            NavigationItem(
                title = stringResource(R.string.menu_market),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Market
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
