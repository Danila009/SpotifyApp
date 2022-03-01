package com.example.spotifyfirebase.navigation.navGraph.homeNavGraph.constants

sealed class RouteScreenHome(val route:String) {
    object Home:RouteScreenHome("home_screen")
}