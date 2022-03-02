package com.example.spotifyfirebase.api.repository

import com.example.spotifyfirebase.api.SpotifyApi
import com.example.spotifyfirebase.api.model.user.Authorization
import com.example.spotifyfirebase.api.model.user.User
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val spotifyApi: SpotifyApi
) {

    suspend fun authorization(
        authorization: Authorization
    ) = spotifyApi.postAuthorization(
        authorization =  authorization
    )

    suspend fun registration(
        user: User
    ) = spotifyApi.postRegistration(
        user = user
    )

    suspend fun getUserInfo():Response<User> = spotifyApi.getUserInfo()
}