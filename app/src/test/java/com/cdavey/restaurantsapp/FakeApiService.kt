package com.cdavey.restaurantsapp

import com.cdavey.restaurantsapp.restaurants.DummyContent
import com.cdavey.restaurantsapp.restaurants.data.remote.RemoteRestaurant
import com.cdavey.restaurantsapp.restaurants.data.remote.RestaurantsApiService
import kotlinx.coroutines.delay

class FakeApiService: RestaurantsApiService {
    override suspend fun getRestaurants(): List<RemoteRestaurant> {
        delay(1000)
        return DummyContent.getRemoteRestaurants()
    }

    override suspend fun getRestaurant(id: Int): Map<String, RemoteRestaurant> {
        TODO("Not yet implemented")
    }
}