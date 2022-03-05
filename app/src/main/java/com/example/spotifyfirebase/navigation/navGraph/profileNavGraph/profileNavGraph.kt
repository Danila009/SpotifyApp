package com.example.spotifyfirebase.navigation.navGraph.profileNavGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.spotifyfirebase.navigation.navGraph.loginNavGraph.loginNavGraph
import com.example.spotifyfirebase.navigation.navGraph.profileNavGraph.constants.ProfileRoute.Route.PROFILE_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.profileNavGraph.constants.ProfileRouteScreen
import com.example.spotifyfirebase.screen.profileScreen.ProfileScreen
import com.example.spotifyfirebase.screen.profileScreen.UserSettingScreen

fun NavGraphBuilder.profileNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = ProfileRouteScreen.ProfileScreen.route,
        route = PROFILE_ROUTE,
        builder = {
            loginNavGraph(
                navController = navController
            )

            composable(ProfileRouteScreen.ProfileScreen.route){
                ProfileScreen(
                    navController = navController
                )
            }

            composable(ProfileRouteScreen.UserSetting.route){
                UserSettingScreen(
                    navController = navController
                )
            }
        }
    )
}