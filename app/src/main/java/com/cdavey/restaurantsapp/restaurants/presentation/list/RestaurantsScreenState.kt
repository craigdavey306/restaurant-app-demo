package com.cdavey.restaurantsapp.restaurants.presentation.list

import com.cdavey.restaurantsapp.restaurants.domain.Restaurant

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)