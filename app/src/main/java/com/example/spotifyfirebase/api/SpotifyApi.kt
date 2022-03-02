package com.example.spotifyfirebase.api

import com.example.spotifyfirebase.api.model.user.Authorization
import com.example.spotifyfirebase.api.model.user.Header
import com.example.spotifyfirebase.api.model.user.User
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.AUTHORIZATION_URL
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.REGISTRATION_URL
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.USER_INFO_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SpotifyApi {

    @POST(AUTHORIZATION_URL)
    suspend fun postAuthorization(
        @Body authorization: Authorization
    ):Response<Header>

    @POST(REGISTRATION_URL)
    suspend fun postRegistration(
        @Body user: User
    )

    @GET(USER_INFO_URL)
    suspend fun getUserInfo():Response<User>
}