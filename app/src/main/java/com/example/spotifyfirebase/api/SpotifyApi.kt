package com.example.spotifyfirebase.api

import com.example.spotifyfirebase.api.model.playlist.Playlist
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.api.model.user.Authorization
import com.example.spotifyfirebase.api.model.user.Header
import com.example.spotifyfirebase.api.model.user.User
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.AUTHORIZATION_URL
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.MUSIC_ID_URL
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.MUSIC_URL
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.PLAYLIST_ID_URL
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.PLAYLIST_URL
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.REGISTRATION_URL
import com.example.spotifyfirebase.api.utils.ConstantsUrlApi.USER_INFO_URL
import retrofit2.Response
import retrofit2.http.*

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

    @GET(PLAYLIST_URL)
    suspend fun getPlaylist():Response<List<Playlist>>

    @GET(PLAYLIST_ID_URL)
    suspend fun getPlaylistItem(
        @Path("id") id:Int
    ):Response<Playlist>

    @GET(MUSIC_URL)
    suspend fun getMusic(
        //@Query("idGnre") idGnre:Int? = null,
        //@Query("idPerson") idPerson:Int? = null,
        @Query("search") search:String = ""
    ):Response<List<Music>>

    @GET(MUSIC_ID_URL)
    suspend fun getMusicItem(
        @Path("id") id: Int
    ):Response<Music>
}