package com.cdavey.restaurantsapp.restaurants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.cdavey.restaurantsapp.restaurants.presentation.details.RestaurantDetailsScreen
import com.cdavey.restaurantsapp.restaurants.presentation.list.RestaurantsScreen
import com.cdavey.restaurantsapp.restaurants.presentation.list.RestaurantsViewModel
import com.cdavey.restaurantsapp.ui.theme.RestaurantsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                        RestaurantsScreen(Modifier.padding(innerPadding))
                    RestaurantsApp(innerPadding)
                }
            }
        }
    }
}

/** Add all application screen here */
@Composable
private fun RestaurantsApp(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "restaurants") {
        composable(route = "restaurants") {
            val viewModel: RestaurantsViewModel = hiltViewModel()
            RestaurantsScreen(
                state = viewModel.state.value,
                onItemClick = { id ->
                    navController.navigate("restaurants/$id")
                },
                onFavoriteClick = { id, oldValue -> viewModel.toggleFavorite(id, oldValue) }
            )
        }
        composable(
            "restaurants/{restaurant_id}",
            arguments = listOf(
                navArgument("restaurant_id") {
                    type = NavType.IntType
                }
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern = "www.restaurantsapp.details.com/{restaurant_id}"
            })
        ) {
            RestaurantDetailsScreen()
        }
    }


}

