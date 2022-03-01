package com.example.spotifyfirebase.data.model

data class Music(
    val date:String = "",
    val title:String = "",
    val musicUrl:String = "",
    val iconUrl:String = "",
    val autors:List<String> = listOf()
)