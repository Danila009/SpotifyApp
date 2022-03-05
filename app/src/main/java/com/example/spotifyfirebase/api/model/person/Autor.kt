package com.example.spotifyfirebase.api.model.person

import com.example.spotifyfirebase.api.model.playlist.Playlist

data class Autor(
    val id:Int? = null,
    val name:String = "",
    val posterUrl:String = "",
    val birthday:String = "",
    val death:String = "",
    val age:Int = 0,
    val playlists:List<Playlist> = listOf()
)
