package com.makeev.cima.presentation.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.makeev.cima.presentation.screens.detail.DetailScreen
import com.makeev.cima.presentation.screens.favourite.FavouriteScreen
import com.makeev.cima.presentation.screens.home.HomeScreen
import com.makeev.cima.presentation.screens.home.HomeScreenBottomBar
import com.makeev.cima.presentation.screens.person.PersonScreen
import com.makeev.cima.presentation.screens.profile.ProfileScreen
import com.makeev.cima.presentation.screens.search.SearchScreen

@Composable
fun NavGraph() {
    var isBottomBarVisible by remember { mutableStateOf(true) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y < 0) isBottomBarVisible = false
                else if (available.y > 0) isBottomBarVisible = true
                return Offset.Zero
            }
        }
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomBarRoutes

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(
                route = Screen.Home.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
            ) {
                HomeScreen(
                    onMovieClick = {
                        navController.navigate(Screen.Detail.createRoute(it.id))
                    },
                    onSearchClick = {
                        navController.navigate(Screen.Search.route) {
                        }
                    }
                )
            }
            composable(
                Screen.Detail.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) {
                DetailScreen(
                    onSimilarMovieClick = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    },
                    onPersonClick = { id ->
                        navController.navigate(Screen.Person.createRoute(id))
                    })
                Log.d("NavGraph", "NavGraph: isClicked  ")
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Favourites.route) {
                FavouriteScreen()
            }
            composable(
                Screen.Person.route,
                arguments = listOf(navArgument("personId") { type = NavType.IntType })
            ) {
                PersonScreen()
            }
            composable(Screen.Search.route) {
                SearchScreen(
                    onBackClick = { navController.popBackStack() },
                    onMovieClick = { id ->
                        navController.navigate(Screen.Detail.createRoute(id)) {
                            popUpTo(Screen.Search.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = isBottomBarVisible && showBottomBar,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            HomeScreenBottomBar(navController = navController, currentRoute = currentRoute)
        }
    }
}

private val bottomBarRoutes = setOf(
    Screen.Home.route,
    Screen.Favourites.route,
    Screen.Profile.route,
)

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int): String = "detail/$movieId"
    }

    data object Search : Screen("search")

    data object Person : Screen("actor/{personId}") {

        fun createRoute(personId: Int): String = "actor/$personId"

    }

    data object Favourites : Screen("favourites")

    data object Profile : Screen("profile")
}