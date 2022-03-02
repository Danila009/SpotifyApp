package com.example.spotifyfirebase.navigation.navGraph.loginNavGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.spotifyfirebase.navigation.navGraph.loginNavGraph.constants.LoginRoute.Route.LOGIN_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.loginNavGraph.constants.LoginRouteScreen
import com.example.spotifyfirebase.screen.loginScreen.AuthorizationScreen
import com.example.spotifyfirebase.screen.loginScreen.LoginScreen
import com.example.spotifyfirebase.screen.loginScreen.RegistrationScreen

fun NavGraphBuilder.loginNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = LoginRouteScreen.Login.route,
        route = LOGIN_ROUTE,
        builder = {
            composable(LoginRouteScreen.Login.route){
                LoginScreen(
                    navController = navController
                )
            }
            composable(LoginRouteScreen.Authorization.route){
                AuthorizationScreen(
                    navController = navController
                )
            }
            composable(LoginRouteScreen.Registration.route){
                RegistrationScreen(
                    navController = navController
                )
            }
        }
    )
}