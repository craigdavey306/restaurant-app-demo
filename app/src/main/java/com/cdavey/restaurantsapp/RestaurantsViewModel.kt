package com.cdavey.restaurantsapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class RestaurantsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    val state = mutableStateOf(mockRestaurants.restoreSelections())

    fun toggleFavorite(id: Int) {
        val restaurants = state.value.toMutableList()
        val itemIndex = restaurants.indexOfFirst { it.id == id }
        val item = restaurants[itemIndex]
        restaurants[itemIndex] = item.copy(isFavorite = !item.isFavorite)
        storeSelection(restaurants[itemIndex])
        state.value = restaurants
    }

    private fun storeSelection(item: Restaurant) {
        val savedToggled = stateHandle.get<List<Int>?>(FAVORITES)
            .orEmpty().toMutableList()
        if (item.isFavorite) {
            savedToggled.add(item.id)
        } else {
            savedToggled.remove(item.id)
        }
        stateHandle[FAVORITES] = savedToggled
    }

    private fun List<Restaurant>.restoreSelections(): List<Restaurant> {
        stateHandle.get<List<Int>?>(FAVORITES)?.let { selectedIds ->
            val restaurantMap = this.associateBy { it.id }
            selectedIds.forEach { id ->
                restaurantMap[id]?.isFavorite = true
            }
            return restaurantMap.values.toList()
        }
        return this
    }

    companion object {
        const val FAVORITES = "favorites"
    }
}