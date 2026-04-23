package com.cdavey.restaurantsapp.restaurants.data

import com.cdavey.restaurantsapp.restaurants.data.di.IoDispatcher
import com.cdavey.restaurantsapp.restaurants.domain.Restaurant
import com.cdavey.restaurantsapp.restaurants.data.local.LocalRestaurant
import com.cdavey.restaurantsapp.restaurants.data.local.PartialLocalRestaurant
import com.cdavey.restaurantsapp.restaurants.data.local.RestaurantDao
import com.cdavey.restaurantsapp.restaurants.data.remote.RestaurantsApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepository @Inject constructor(
    private val restInterface: RestaurantsApiService,
    private val restaurantsDao: RestaurantDao,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun loadAllRestaurants(): Unit {
        return withContext(dispatcher) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (restaurantsDao.getAll()
                                .isEmpty()
                        ) throw Exception("Something went wrong. We have no data.")
                    }

                    else -> throw e
                }
            }
        }
    }

    suspend fun getRestaurants(): List<Restaurant> {
        return withContext(dispatcher) {
            return@withContext restaurantsDao.getAll().map {
                Restaurant(it.id, it.title, it.description, it.isFavorite)
            }
        }
    }

    suspend fun toggleFavoriteRestaurant(id: Int, value: Boolean) =
        withContext(dispatcher) {
            restaurantsDao.update(
                PartialLocalRestaurant(id = id, isFavorite = value)
            )
        }

    suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        val favoriteRestaurants = restaurantsDao.getAllFavorited()
        restaurantsDao.addAll(
            remoteRestaurants.map { LocalRestaurant(it.id, it.title, it.description, false) }
        )
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialLocalRestaurant(it.id, true)
            }
        )
    }
}