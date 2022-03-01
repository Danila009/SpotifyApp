package com.example.spotifyfirebase.screen.homeScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyfirebase.data.MusicDatabase
import com.example.spotifyfirebase.data.model.Music
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    //private val musicDatabase: MusicDatabase
):ViewModel() {
    private val _responseAllMusic:MutableStateFlow<List<Music>> = MutableStateFlow(listOf())
    val responseAllMusic: StateFlow<List<Music>> = _responseAllMusic.asStateFlow()

    //Test firebase
    private val musicDatabase:MusicDatabase = MusicDatabase()

    fun getAllMusic(){
        viewModelScope.launch {
            _responseAllMusic.value = musicDatabase.getAllMusic()
        }
    }
}