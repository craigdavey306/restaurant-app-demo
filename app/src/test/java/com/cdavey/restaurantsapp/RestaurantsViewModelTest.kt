package com.cdavey.restaurantsapp

import com.cdavey.restaurantsapp.restaurants.DummyContent
import com.cdavey.restaurantsapp.restaurants.data.RestaurantsRepository
import com.cdavey.restaurantsapp.restaurants.domain.GetInitialRestaurantsUseCase
import com.cdavey.restaurantsapp.restaurants.domain.GetSortedRestaurantsUseCase
import com.cdavey.restaurantsapp.restaurants.domain.ToggleRestaurantUseCase
import com.cdavey.restaurantsapp.restaurants.presentation.list.RestaurantsScreenState
import com.cdavey.restaurantsapp.restaurants.presentation.list.RestaurantsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: org.junit.runner.Description) = Dispatchers.setMain(testDispatcher)
    override fun finished(description: org.junit.runner.Description) = Dispatchers.resetMain()
}

@ExperimentalCoroutinesApi
class RestaurantsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun initialState_isProduced() = scope.runTest {
        val viewModel = getViewModel()
        val initialState = viewModel.state.value
        assert(
            initialState == RestaurantsScreenState(
                restaurants = emptyList(),
                isLoading = true,
                error = null
            )
        )
    }

    @Test
    fun stateWithContent_isProduced() = scope.runTest {
        val viewModel = getViewModel()
        advanceUntilIdle()
        val currentState = viewModel.state.value
        assert(
            currentState == RestaurantsScreenState(
                restaurants = DummyContent.getDomainRestaurants(),
                isLoading = false,
                error = null
            )
        )
    }

    private fun getViewModel(): RestaurantsViewModel {
        val restaurantsRepository = RestaurantsRepository(
            FakeApiService(),
            FakeRoomDao(),
            dispatcher
        )
        val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase(restaurantsRepository)
        val getInitialRestaurantsUseCase = GetInitialRestaurantsUseCase(
            restaurantsRepository,
            getSortedRestaurantsUseCase
        )
        val toggleRestaurantUseCase = ToggleRestaurantUseCase(
            restaurantsRepository,
            getSortedRestaurantsUseCase,
        )

        return RestaurantsViewModel(
            getInitialRestaurantsUseCase,
            toggleRestaurantUseCase,
            dispatcher
        )
    }
}