package com.example.spotifyfirebase.navigation.navGraph.searchNavGraph.constants

sealed class SearchScreenRoute(val route:String){
    object Search:SearchScreenRoute("search_screen")
}
