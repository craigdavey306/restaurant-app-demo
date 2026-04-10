package com.cdavey.restaurantsapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RestaurantDetailScreen(modifier: Modifier) {
    val viewModel: RestaurantDetailsViewModel = viewModel()
    val item = viewModel.state.value
    if (item != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth(0.85f)
        ) {
            RestaurantIcon(Icons.Filled.Place, Modifier.padding(top = 32.dp, bottom = 32.dp))

            RestaurantDetails(
                item.title, item.description, Modifier.padding(bottom = 32.dp),
                Alignment.CenterHorizontally
            )

            Text("More inf coming soon!")
        }
    }
}