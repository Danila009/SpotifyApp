package com.example.spotifyfirebase.navigation.navGraph.musicNavGraph

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRoute.Argument.IMAGE_URL
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRoute.Argument.MUSIC_ID
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRoute.Argument.PLAYLIST_ID
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRoute.Route.MUSIC_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRouteScreen
import com.example.spotifyfirebase.screen.musicScreen.ImageZoomScreen
import com.example.spotifyfirebase.screen.musicScreen.MusicInfoScreen
import com.example.spotifyfirebase.screen.musicScreen.MusicPlaylistScreen

fun NavGraphBuilder.musicNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = MusicRouteScreen.MusicPlaylist.route,
        route = MUSIC_ROUTE,
        builder = {
            composable(
                MusicRouteScreen.MusicPlaylist.route,
                arguments = listOf(
                    navArgument(
                        name = PLAYLIST_ID,
                        builder = {
                            type = NavType.IntType
                        }
                    )
                )
            ){
                MusicPlaylistScreen(
                    navController = navController,
                    idPlaylist = it.arguments?.getInt(PLAYLIST_ID)!!.toInt()
                )
            }
            composable(
                MusicRouteScreen.MusicInfo.route,
                arguments = listOf(
                    navArgument(
                        name = MUSIC_ID,
                        builder = {
                            type = NavType.IntType
                        }
                    )
                )
            ){
                MusicInfoScreen(
                    navController = navController,
                    idMusic = it.arguments?.getInt(MUSIC_ID)!!.toInt()
                )
            }
            composable(
                MusicRouteScreen.ImageZoomScreen.route,
                arguments = listOf(
                    navArgument(
                        name = IMAGE_URL,
                        builder = {
                            type = NavType.StringType
                        }
                    ), navArgument(
                        name = MUSIC_ID,
                        builder = {
                            type = NavType.IntType
                        }
                    )
                )
            ){
                ImageZoomScreen(
                    navController = navController,
                    imageUrl = it.arguments?.getString(IMAGE_URL).toString(),
                    idMusic = it.arguments?.getInt(MUSIC_ID)!!.toInt()
                )
            }
        }
    )
}