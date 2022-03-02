package com.example.spotifyfirebase.navigation.navGraph.loginNavGraph.constants

sealed class LoginRouteScreen(val route:String) {
    object Login:LoginRouteScreen("login_screen")
    object Authorization:LoginRouteScreen("authorization_screen")
    object Registration:LoginRouteScreen("registration_screen")
}