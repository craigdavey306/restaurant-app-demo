package com.cdavey.restaurantsapp.restaurants.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.cdavey.restaurantsapp.restaurants.presentation.list.RestaurantsViewModel
import com.cdavey.restaurantsapp.restaurants.domain.Restaurant
import com.cdavey.restaurantsapp.restaurants.presentation.Description

@Composable
fun RestaurantsScreen(
    state: RestaurantsScreenState,
    onItemClick: (id: Int) -> Unit,
    onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit
) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.restaurants) { restaurant ->
                    RestaurantItem(
                        restaurant,
                        onFavoriteClick = { id, oldValue -> onFavoriteClick(id, oldValue) },
                        onItemClick = { id -> onItemClick(id) })
                }
            }
            if (state.isLoading) CircularProgressIndicator(
                Modifier.semantics {
                    this.contentDescription = Description.RESTAURANTS_LOADING
                }
            )

            if (state.error != null) Text(state.error)
        }
    }
}

@Composable
fun RestaurantItem(
    item: Restaurant,
    onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit,
    onItemClick: (id: Int) -> Unit
) {
    val icon = when {
        item.isFavorite -> Icons.Filled.Favorite
        else -> Icons.Filled.FavoriteBorder
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            RestaurantIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            RestaurantDetails(
                item.title,
                item.description,
                Modifier.weight(0.7f)
            )
            RestaurantIcon(icon, Modifier.weight(0.15f)) {
                onFavoriteClick(item.id, item.isFavorite)
            }
        }
    }
}

@Composable
fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onclick: () -> Unit = { }) {
    Image(
        imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onclick() }
    )
}

@Composable
fun RestaurantDetails(
    title: String,
    description: String,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = description,
            color = LocalContentColor.current.copy(alpha = 0.70f),
            style = MaterialTheme.typography.bodySmall
        )
    }
}


