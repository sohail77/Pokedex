package com.sohail.pokedex.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sohail.pokedex.ui.detailScreen.DetailScreen
import com.sohail.pokedex.ui.listScreen.ListViewModel
import com.sohail.pokedex.ui.listScreen.PokemonListScreen

sealed class Screen(val route: String) {
    object ListScreen: Screen("List")
    object DetailScreen: Screen("Detail/{pokemonId}") {
        fun createRoute(id: Int) = "Detail/$id"
    }
}


@ExperimentalFoundationApi
@Composable
fun PokemonApp(viewModel: ListViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ListScreen.route) {
        composable(route = Screen.ListScreen.route) {
            PokemonListScreen(viewModel = viewModel, navController)
        }

        composable(route = Screen.DetailScreen.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("pokemonId")
            DetailScreen(name = id!!)
        }
    }
}