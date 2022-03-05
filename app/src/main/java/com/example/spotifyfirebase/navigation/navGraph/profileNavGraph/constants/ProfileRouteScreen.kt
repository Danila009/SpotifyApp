package com.example.spotifyfirebase.navigation.navGraph.profileNavGraph.constants

sealed class ProfileRouteScreen(val route:String){
    object ProfileScreen:ProfileRouteScreen("profile_screen")
    object UserSetting:ProfileRouteScreen("user_setting_screen")
}
