package com.example.spotifyfirebase.screen.autorScreen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyfirebase.api.model.person.Autor
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
class AutorViewModel @Inject constructor(
    private val apiRepository: ApiRepository
):ViewModel() {
    private val _responseAutorItem:MutableStateFlow<Autor> = MutableStateFlow(Autor())
    val responseAutorItem: StateFlow<Autor> = _responseAutorItem
    private val _responseAutorMusic:MutableStateFlow<List<Music>> = MutableStateFlow(listOf())
    val responseAutorMusic:StateFlow<List<Music>> = _responseAutorMusic.asStateFlow()

    fun getAutorItem(id:Int){
        viewModelScope.launch {
            try {
                _responseAutorItem.value = apiRepository.getAutorItem(id = id).body()!!
            }catch (e:Exception){
                Log.e(Constants.TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun getAutorMusic(id: Int){
        viewModelScope.launch {
            try {
                _responseAutorMusic.value = apiRepository.getAutorMusic(id = id).body()!!
            }catch (e:Exception){
                Log.e(Constants.TAG_RETROFIT, e.message.toString())
            }
        }
    }
}