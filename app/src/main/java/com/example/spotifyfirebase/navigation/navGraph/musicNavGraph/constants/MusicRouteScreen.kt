package com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants

sealed class MusicRouteScreen(val route:String) {
    object MusicPlaylist:MusicRouteScreen("music_playlist_screen?playlistId={playlistId}"){
        fun data(
            playlistId:Int
        ):String = "music_playlist_screen?playlistId=$playlistId"
    }
    object MusicInfo:MusicRouteScreen("music_info_screen?musicId={musicId}"){
        fun data(
            musicId:Int
        ):String = "music_info_screen?musicId=$musicId"
    }
    object ImageZoomScreen:MusicRouteScreen("image_zoom_screen?imageUrl={imageUrl}&musicId={musicId}"){
        fun data(
            imageUrl:String,
            musicId:Int
        ):String = "image_zoom_screen?imageUrl=$imageUrl&musicId=$musicId"
    }
}