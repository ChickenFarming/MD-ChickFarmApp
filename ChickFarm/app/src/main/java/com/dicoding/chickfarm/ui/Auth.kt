//package com.dicoding.chickfarm.ui
//
//import android.content.Context
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.dicoding.chickfarm.R
//import com.dicoding.chickfarm.navigation.Screen
//import com.dicoding.chickfarm.ui.screen.auth.LoginScreen
//import com.dicoding.chickfarm.ui.screen.auth.LoginScreenTes
//import com.dicoding.chickfarm.ui.screen.auth.RegistrationScreen
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Auth(
//    modifier: Modifier = Modifier,
//    navController: NavHostController = rememberNavController(),
//    context: Context,
//) {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                navigationIcon = {
//                    Icon(
//                        modifier = Modifier.padding(12.dp),
//                        imageVector = Icons.Default.Info,
//                        contentDescription = stringResource(R.string.app_name)
//                    )
//                },
//                title = {
//                    when (currentRoute) {
//                        Screen.Login.route -> Text(stringResource(id = R.string.login))
//                        else -> Text(stringResource(R.string.menu_market))
//                    }
//                },
//                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondary)
//            )
//
//        },
//        modifier = modifier
//    ) { innerPadding ->
//        NavHost(
//            modifier = Modifier
//                .padding(innerPadding)
//                .background(MaterialTheme.colorScheme.secondary),
//            navController = navController,
//            startDestination = Screen.Login.route,
//        ) {
//            composable(Screen.Login.route) {
////                LoginScreen(navController = navController, context = context, modifier = Modifier.background(MaterialTheme.colorScheme.background))
////               LoginScreenTes()
//            }
//            composable(Screen.Signup.route) {
//                RegistrationScreen(navController = navController)
//            }
//
//        }
//
//
//    }
//
//
//}
//
//
