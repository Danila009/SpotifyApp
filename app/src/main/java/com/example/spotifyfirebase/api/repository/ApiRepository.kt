package com.example.spotifyfirebase.api.repository

import com.example.spotifyfirebase.api.SpotifyApi
import com.example.spotifyfirebase.api.model.person.Autor
import com.example.spotifyfirebase.api.model.playlist.Playlist
import com.example.spotifyfirebase.api.model.playlist.music.Genre
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.api.model.user.Authorization
import com.example.spotifyfirebase.api.model.user.HistorySearch
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

    suspend fun getPlaylist():Response<List<Playlist>> = spotifyApi.getPlaylist()

    suspend fun getPlaylistItem(id:Int):Response<List<Music>> = spotifyApi.getPlaylistItem(id = id)

    suspend fun getMusic(
        search:String
    ):Response<List<Music>> = spotifyApi.getMusic(
        search = search
    )

    suspend fun getMusicItem(id: Int) = spotifyApi.getMusicItem(id = id)

    suspend fun getMusicGenre(idMusic:Int):Response<List<Genre>> = spotifyApi.getMusicGenre(idMusic)

    suspend fun getMusicAutor(idMusic: Int):Response<List<Autor>> = spotifyApi.getMusicAutor(idMusic)

    suspend fun getAutorItem(id: Int):Response<Autor> = spotifyApi.getAutorItem(id = id)

    suspend fun getAutorMusic(id: Int):Response<List<Music>> = spotifyApi.getAutorMusic(id = id)

    suspend fun getGenre():Response<List<Genre>> = spotifyApi.getGenre()

    suspend fun getGenreMusic(id: Int):Response<List<Music>> = spotifyApi.getGenreMusic(id = id)

    suspend fun getHistoeySearch():Response<List<HistorySearch>> = spotifyApi.getHistoeySearch()

    suspend fun postHistorySearch(historySearch: HistorySearch) = spotifyApi.poatHistorySearch(historySearch)
}