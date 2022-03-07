package com.sohail.pokedex.ui.detailScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(modifier: Modifier = Modifier, name: String) {
    Text(text = name)
}