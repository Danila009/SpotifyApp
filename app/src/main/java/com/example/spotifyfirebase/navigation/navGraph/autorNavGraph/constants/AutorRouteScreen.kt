package com.example.spotifyfirebase.navigation.navGraph.autorNavGraph.constants

sealed class AutorRouteScreen(val route:String){
    object AutorInfo:AutorRouteScreen("autor_info_screen?autorId={autorId}&musicId={musicId}"){
        fun date(
            autorId:Int,
            musicId:Int
        ):String = "autor_info_screen?autorId=$autorId&musicId=$musicId"
    }
}
