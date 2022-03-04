package com.example.spotifyfirebase.api.model.playlist.music

import com.example.spotifyfirebase.api.model.person.Autor

data class Music(
    val id:Int? = null,
    val document:String = "",
    val title:String = "",
    val date:String = "",
    val webIcon:String = "",
    val autors:List<Autor> = listOf(),
    val genre:List<Genre> = listOf()
)
