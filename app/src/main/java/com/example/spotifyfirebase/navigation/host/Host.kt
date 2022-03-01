package com.example.spotifyfirebase.navigation.host

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.spotifyfirebase.navigation.navGraph.homeNavGraph.constants.RouteAndArgumentsHome.Route.HOME_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.homeNavGraph.homeNavGraph

@Composable
fun Host(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = HOME_ROUTE,
        route = "route",
        builder = {
            homeNavGraph(
                navController = navHostController
            )
        }
    )
}