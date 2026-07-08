package com.makeev.cima.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.makeev.cima.presentation.screens.detail.DetailScreen
import com.makeev.cima.presentation.screens.home.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(onMovieClick = {
                navController.navigate(Screen.Detail.createRoute(it.movieId))
            })
        }
        composable(
            Screen.Detail.route,
            arguments = listOf(navArgument("movieId") {type = NavType.IntType})
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailScreen(movieId = movieId)
        }
    }
}


sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int): String = "detail/$movieId"

    }


}