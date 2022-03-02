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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    //private val musicDatabase: MusicDatabase
):ViewModel() {
    private val _responseAllMusic:MutableStateFlow<List<Music>> = MutableStateFlow(listOf())
    val responseAllMusic: StateFlow<List<Music>> = _responseAllMusic.asStateFlow()
    private val _responseTimeText:MutableStateFlow<String> = MutableStateFlow("")
    val responseTimeText:StateFlow<String> = _responseTimeText.asStateFlow()

    //Test firebase
    private val musicDatabase:MusicDatabase = MusicDatabase()

    //Test firebase
    fun getAllMusic(){
        viewModelScope.launch {
            _responseAllMusic.value = musicDatabase.getAllMusic()
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
}