package com.example.spotifyfirebase.api.model.person

data class Autor(
    val id:Int? = null,
    val name:String,
    val posterUrl:String,
    val birthday:String,
    val death:String,
    val age:Int
)
