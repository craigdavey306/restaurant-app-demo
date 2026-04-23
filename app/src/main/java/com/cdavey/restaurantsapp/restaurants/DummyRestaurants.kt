package com.cdavey.restaurantsapp.restaurants

import com.cdavey.restaurantsapp.restaurants.data.remote.RemoteRestaurant
import com.cdavey.restaurantsapp.restaurants.domain.Restaurant



object DummyContent {
    fun getDomainRestaurants() = (0..3).map {
        Restaurant(id = it, title = "title$it", description = "description$it", isFavorite = false)
    }

    fun getRemoteRestaurants() = getDomainRestaurants().map {
        RemoteRestaurant(it.id, it.title, it.description)
    }
}