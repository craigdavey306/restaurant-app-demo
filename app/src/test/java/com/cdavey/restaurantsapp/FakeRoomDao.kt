package com.cdavey.restaurantsapp

import com.cdavey.restaurantsapp.restaurants.data.local.LocalRestaurant
import com.cdavey.restaurantsapp.restaurants.data.local.PartialLocalRestaurant
import com.cdavey.restaurantsapp.restaurants.data.local.RestaurantDao
import kotlinx.coroutines.delay

class FakeRoomDao: RestaurantDao {
    private var restaurants = HashMap<Int, LocalRestaurant>()

    override suspend fun getAll(): List<LocalRestaurant> {
        delay(1000)
        return restaurants.values.toList()
    }

    override suspend fun addAll(restaurants: List<LocalRestaurant>) {
        restaurants.forEach {
            this.restaurants[it.id] = it
        }
    }

    override suspend fun update(partialLocalRestaurant: PartialLocalRestaurant) {
        delay(1000)
        updateRestaurant(partialLocalRestaurant)
    }

    override suspend fun updateAll(
        partialRestaurants: List<PartialLocalRestaurant>
    ) {
        delay(1000)
        partialRestaurants.forEach { updateRestaurant(it) }
    }

    override suspend fun getAllFavorited()
            : List<LocalRestaurant> {
        return restaurants.values.toList()
            .filter { it.isFavorite }
    }

    private fun updateRestaurant(
        partialRestaurant: PartialLocalRestaurant
    ) {
        val restaurant = this.restaurants[partialRestaurant.id]
        if (restaurant != null)
            this.restaurants[partialRestaurant.id] =
                restaurant.copy(isFavorite = partialRestaurant.isFavorite)
    }
}