package com.dicoding.chickfarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.dicoding.chickfarm.ui.ChickFarmApp
import com.dicoding.chickfarm.ui.theme.ChickFarmTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            ChickFarmTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  val navigateToMarket = remember{ mutableStateOf(intent.getBooleanExtra("navigateToMarket", false)) }
                    ChickFarmApp(
                        context = this,
                        navigateToMarket = navigateToMarket,
                        searchValue = intent.getStringExtra("searchValue")
                    )
                }

            }
        }
    }
}

