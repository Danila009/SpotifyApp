package com.example.spotifyfirebase.screen.musicScreen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyfirebase.api.model.person.Autor
import com.example.spotifyfirebase.api.model.playlist.Playlist
import com.example.spotifyfirebase.api.model.playlist.music.Genre
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.api.repository.ApiRepository
import com.example.spotifyfirebase.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val apiRepository: ApiRepository
):ViewModel(){
    private val _responsePlaylistItem:MutableStateFlow<List<Music>> = MutableStateFlow(listOf())
    val responsePlaylistItem: StateFlow<List<Music>> = _responsePlaylistItem.asStateFlow()
    private val _responseMusicItem:MutableStateFlow<Music> = MutableStateFlow(Music())
    val responseMusicItem:StateFlow<Music> = _responseMusicItem.asStateFlow()
    private val _responseGenreMusic:MutableStateFlow<List<Music>> = MutableStateFlow(listOf())
    val responseGenreMusic:StateFlow<List<Music>> = _responseGenreMusic.asStateFlow()
    private val _responseMusicGenre:MutableStateFlow<List<Genre>> = MutableStateFlow(listOf())
    val responseMusicGenre:StateFlow<List<Genre>> = _responseMusicGenre.asStateFlow()
    private val _responseMusicAutor:MutableStateFlow<List<Autor>> = MutableStateFlow(listOf())
    val responseMusicAutor:StateFlow<List<Autor>> = _responseMusicAutor.asStateFlow()

    fun getPlaylistItem(id:Int){
        viewModelScope.launch {
            try {
                _responsePlaylistItem.value = apiRepository.getPlaylistItem(id = id).body()!!
            }catch (e:Exception){
                Log.e(Constants.TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun getMusicItem(id: Int){
        viewModelScope.launch {
            try {
                _responseMusicItem.value = apiRepository.getMusicItem(id = id).body()!!
            }catch (e:Exception){
                Log.e(Constants.TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun getGenreMusic(id:Int){
        viewModelScope.launch {
            try {
                _responseGenreMusic.value = apiRepository.getGenreMusic(id = id).body()!!
            }catch (e:Exception){
                Log.e(Constants.TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun getMusicGenre(idMusic:Int){
        viewModelScope.launch {
            try {
                _responseMusicGenre.value = apiRepository.getMusicGenre(idMusic).body()!!
            }catch (e:Exception){
                Log.e(Constants.TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun getMusicAutor(idMusic: Int){
        viewModelScope.launch {
            try {
                _responseMusicAutor.value = apiRepository.getMusicAutor(idMusic).body()!!
            }catch (e:Exception){
                Log.e(Constants.TAG_RETROFIT, e.message.toString())
            }
        }
    }
}