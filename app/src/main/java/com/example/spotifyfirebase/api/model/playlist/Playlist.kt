package com.example.spotifyfirebase.api.model.playlist

import com.example.spotifyfirebase.api.model.playlist.music.Music

data class Playlist(
    val id:Int? = null,
    val title:String = "",
    val icon:String = "",
    val musics:List<Music> = listOf()
)
