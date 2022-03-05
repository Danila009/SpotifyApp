package com.example.spotifyfirebase.navigation.navGraph.autorNavGraph

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.spotifyfirebase.navigation.navGraph.autorNavGraph.constants.AutorRoute.Argument.AUTOR_ID
import com.example.spotifyfirebase.navigation.navGraph.autorNavGraph.constants.AutorRoute.Route.AUTOR_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.autorNavGraph.constants.AutorRouteScreen
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRoute.Argument.MUSIC_ID
import com.example.spotifyfirebase.screen.autorScreen.AutorInfoScreen

fun NavGraphBuilder.autorNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = AutorRouteScreen.AutorInfo.route,
        route = AUTOR_ROUTE,
        builder = {
            composable(
                AutorRouteScreen.AutorInfo.route,
                arguments = listOf(
                    navArgument(AUTOR_ID){
                        type = NavType.IntType
                    }, navArgument(MUSIC_ID){
                        type = NavType.IntType
                    }
                )
            ){
                AutorInfoScreen(
                    navController = navController,
                    idAutor = it.arguments!!.getInt(AUTOR_ID),
                    musicId = it.arguments!!.getInt(MUSIC_ID)
                )
            }
        }
    )
}