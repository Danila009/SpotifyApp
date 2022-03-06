package com.example.spotifyfirebase.navigation.navGraph.homeNavGraph

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.spotifyfirebase.navigation.navGraph.homeNavGraph.constants.RouteAndArgumentsHome.Route.HOME_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.homeNavGraph.constants.RouteScreenHome
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.musicNavGraph
import com.example.spotifyfirebase.screen.homeScreen.HomeScreen
import com.example.spotifyfirebase.screen.homeScreen.viewModel.HomeViewModel

@ExperimentalFoundationApi
fun NavGraphBuilder.homeNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = RouteScreenHome.Home.route,
        route = HOME_ROUTE,
        builder = {

            musicNavGraph(
                navController = navController
            )

            composable(RouteScreenHome.Home.route){
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(
                    homeViewModel = homeViewModel,
                    navController = navController
                )
            }
        }
    )
}