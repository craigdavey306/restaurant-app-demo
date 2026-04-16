package com.cdavey.restaurantsapp.restaurants.domain

import com.cdavey.restaurantsapp.restaurants.data.RestaurantsRepository
import javax.inject.Inject

class ToggleRestaurantUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantUseCase: GetSortedRestaurantsUseCase
) {


    suspend operator fun invoke(id: Int, oldValue: Boolean): List<Restaurant> {
        val newFavorite = oldValue.not()
        repository.toggleFavoriteRestaurant(id, newFavorite)
        return getSortedRestaurantUseCase()
    }
}