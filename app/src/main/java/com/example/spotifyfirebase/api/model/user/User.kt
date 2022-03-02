package com.example.spotifyfirebase.api.model.user

import com.example.spotifyfirebase.api.model.music.Music

data class User(
    val id:Int? = null,
    val username:String = "",
    val email:String = "",
    val password:String = "",
    val favoriteMusics:List<Music> = listOf()
)
