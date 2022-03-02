package com.example.spotifyfirebase.navigation.navGraph.searchNavGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.spotifyfirebase.navigation.navGraph.searchNavGraph.constants.RouteSearch.Route.SEARCH_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.searchNavGraph.constants.SearchScreenRoute
import com.example.spotifyfirebase.screen.searchScreen.SearchScreen

fun NavGraphBuilder.searchNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = SearchScreenRoute.Search.route,
        route = SEARCH_ROUTE,
        builder = {
            composable(SearchScreenRoute.Search.route){
                SearchScreen(
                    navController = navController
                )
            }
        }
    )
}