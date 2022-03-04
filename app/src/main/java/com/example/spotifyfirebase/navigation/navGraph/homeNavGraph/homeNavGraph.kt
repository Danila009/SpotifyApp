package com.example.spotifyfirebase.navigation.navGraph.homeNavGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.spotifyfirebase.navigation.navGraph.homeNavGraph.constants.RouteAndArgumentsHome.Route.HOME_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.homeNavGraph.constants.RouteScreenHome
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.musicNavGraph
import com.example.spotifyfirebase.screen.homeScreen.HomeScreen

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
                HomeScreen(
                    navController = navController
                )
            }
        }
    )
}