package com.example.spotifyfirebase.screen.homeScreen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyfirebase.api.model.playlist.Playlist
import com.example.spotifyfirebase.api.model.playlist.music.Genre
import com.example.spotifyfirebase.api.repository.ApiRepository
import com.example.spotifyfirebase.data.MusicDatabase
import com.example.spotifyfirebase.data.model.Music
import com.example.spotifyfirebase.utils.Constants.TAG_RETROFIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiRepository: ApiRepository
):ViewModel() {
    private val _responseAllMusic:MutableStateFlow<List<Music>> = MutableStateFlow(listOf())
    val responseAllMusic: StateFlow<List<Music>> = _responseAllMusic.asStateFlow()
    private val _responseTimeText:MutableStateFlow<String> = MutableStateFlow("")
    val responseTimeText:StateFlow<String> = _responseTimeText.asStateFlow()
    private val _responsePlaylist:MutableStateFlow<List<Playlist>> = MutableStateFlow(listOf())
    val responsePlaylist:StateFlow<List<Playlist>> = _responsePlaylist.asStateFlow()
    private val _responseGenre:MutableStateFlow<List<Genre>> = MutableStateFlow(listOf())
    val responseGenre:StateFlow<List<Genre>> = _responseGenre.asStateFlow()

    //Test firebase
    private val musicDatabase:MusicDatabase = MusicDatabase()

    //Test firebase
    fun getAllMusic(){
        viewModelScope.launch {
            _responseAllMusic.value = musicDatabase.getAllMusic()
        }
    }

    fun getPlaylist(){
        viewModelScope.launch {
            try {
                _responsePlaylist.value = apiRepository.getPlaylist().body()!!
            }catch (e:Exception){
                Log.e(TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun getCurrentTimeText(){
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("hh", Locale.getDefault())
        when(formatter.format(time).toInt()){
            in 0..5-> _responseTimeText.value = "Доброй ночи!"
            in 5..11-> _responseTimeText.value = "Доброе утро!"
            in 11..17-> _responseTimeText.value = "Доброго дня!"
            in 17..21 -> _responseTimeText.value = "Доброго вечера!"
            in 21..24 -> _responseTimeText.value = "Доброй ночи!"
        }
    }

    fun getGenre(){
        viewModelScope.launch {
            try {
                _responseGenre.value = apiRepository.getGenre().body()!!
            }catch (e:Exception){
                Log.e(TAG_RETROFIT, e.message.toString())
            }
        }
    }
}