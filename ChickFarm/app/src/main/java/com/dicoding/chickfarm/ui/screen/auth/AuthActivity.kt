package com.dicoding.chickfarm.ui.screen.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dicoding.chickfarm.MainActivity
import com.dicoding.chickfarm.R
import com.dicoding.chickfarm.ViewModelFactory
import com.dicoding.chickfarm.data.retrofit.ApiConfig
import com.dicoding.chickfarm.navigation.Screen
import com.dicoding.chickfarm.ui.component.ButtonLarge
import com.dicoding.chickfarm.ui.screen.utils.Utils
import com.dicoding.chickfarm.ui.theme.ChickFarmTheme
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChickFarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Auth(context = this)

                }
            }
        }

    }

    private val apiService = ApiConfig().getApiService()
    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(apiService, this)
    }

    private fun performLogin(email: String?, password: String?) {
        lifecycleScope.launch {
            try {
                val response = viewModel.getAllUsers()
                Log.d("getAllUsers", response.data.toString())

                val userMatched =
                    response.data?.find { it?.email == email && it?.password == password }

                if (userMatched != null) {
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    Utils.setLoginStatus(this@AuthActivity, true)
                    Toast.makeText(
                        this@AuthActivity,
                        "Selamat Datang ${userMatched.username}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else Toast.makeText(this@AuthActivity, "Anda belum mendaftar", Toast.LENGTH_SHORT)
                    .show()


            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Toast.makeText(this@AuthActivity, errorBody, Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Auth(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        context: Context,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

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
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondary)
                )

            },
            modifier = modifier
        ) { innerPadding ->
            NavHost(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.secondary),
                navController = navController,
                startDestination = Screen.Login.route,
            ) {
                composable(Screen.Login.route) {
                    LoginScreen(
                        navController = navController,
                        context = context,
                        modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    )
//               LoginScreenTes()
                }
                composable(Screen.Signup.route) {
                    RegistrationScreen(navController = navController)
                }

            }


        }


    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen(
        modifier: Modifier = Modifier,
        navController: NavHostController,
        context: Context,
    ) {
//        val viewModel: LoginViewModel = viewModel(factory = ViewModelFactory(Repository(ApiConfig().getApiService())))
//        val sharedPreferences = context.getSharedPreferences("login_status", Context.MODE_PRIVATE)
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),


            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.login),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
            )
            Spacer(modifier = modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Email,
                    modifier = Modifier.padding(8.dp),
                    contentDescription = null
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .padding(8.dp),
                    shape = RoundedCornerShape(26.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next // Set action button to "Next"
                    ),

                    )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Lock,
                    modifier = Modifier
                        .padding(8.dp),
                    contentDescription = null
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {

                        }
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(26.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible }
                        ) {
                            Icon(
                                imageVector = Icons.Default.RemoveRedEye,
                                modifier = Modifier.padding(8.dp),
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
//                nanti disini akan membuat pengecekan login
                    performLogin(email = email, password = password)
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(16.dp))

            ButtonLarge(
                navigate = { navController.navigate(Screen.Signup.route) },
                text = "Signup",
                color = Color(0xFFC95050)
            )
        }
    }


    @Composable
    fun RegistrationScreen(
        modifier: Modifier = Modifier,
        navController: NavHostController
    ) {
        var email by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.signup),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
            )
            Spacer(modifier = modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Email,
                    modifier = Modifier.padding(8.dp),
                    contentDescription = null
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(26.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next, // Set action button to "Next",
                        keyboardType = KeyboardType.Text
                    ),
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Email,
                    modifier = Modifier.padding(8.dp),
                    contentDescription = null
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(26.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next, // Set action button to "Next"
                        keyboardType = KeyboardType.Email
                    ),
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Lock,
                    modifier = Modifier
                        .padding(8.dp),
                    contentDescription = null
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {

                        }
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(26.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible }
                        ) {
                            Icon(
                                imageVector = Icons.Default.RemoveRedEye,
                                modifier = Modifier.padding(8.dp),
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    if(email.isNotEmpty()&& username.isNotEmpty() && password.isNotEmpty()){
                    lifecycleScope.launch {
                        try {
                       viewModel.insertUser(email = email, username = username, password = password)
                            navController.navigate(Screen.Login.route)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    }else{
                        Toast.makeText(this@AuthActivity, "lengkapi semua nya", Toast.LENGTH_SHORT).show()
                    }

                },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }






}






