package com.dicoding.chickfarm.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MarketScreen(
    modifier: Modifier = Modifier
){
    Text(
        text = "Home",
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,

            ),
    )
}