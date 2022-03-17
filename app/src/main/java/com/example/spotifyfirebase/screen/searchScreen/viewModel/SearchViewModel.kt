package com.example.spotifyfirebase.screen.searchScreen.viewModel

import android.media.MediaMetadata
import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.api.model.user.HistorySearch
import com.example.spotifyfirebase.api.repository.ApiRepository
import com.example.spotifyfirebase.exoplayer.MusicServiceConnection
import com.example.spotifyfirebase.exoplayer.isPlayEnabled
import com.example.spotifyfirebase.exoplayer.isPlaying
import com.example.spotifyfirebase.exoplayer.isPrepared
import com.example.spotifyfirebase.utils.Constants
import com.example.spotifyfirebase.utils.Constants.TAG_RETROFIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val musicServiceConnection: MusicServiceConnection
):ViewModel() {
    private val _responseMusic:MutableStateFlow<List<Music>> = MutableStateFlow(listOf())
    val responseMusic: StateFlow<List<Music>> = _responseMusic.asStateFlow()
    private val _responseHistorySearch:MutableStateFlow<List<HistorySearch>> = MutableStateFlow(listOf())
    val responseHistorySearch:StateFlow<List<HistorySearch>> = _responseHistorySearch.asStateFlow()

    private val curPlayingSong = musicServiceConnection.curPlayingSong
    private val playbackState = musicServiceConnection.playbackState

    fun getMusic(
        search:String = ""
    ){
        viewModelScope.launch {
            try {
                _responseMusic.value = apiRepository.getMusic(
                    search = search
                ).body()!!
            }catch (e:Exception){
                Log.e(TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun postHistorySearch(historySearch: HistorySearch){
        viewModelScope.launch {
            try {
                apiRepository.postHistorySearch(historySearch)
            }catch (e:Exception){
                Log.e(TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun getHistorySearch(){
        viewModelScope.launch {
            try {
                _responseHistorySearch.value = apiRepository.getHistoeySearch().body()!!
            }catch (e:Exception){
                Log.e(TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun playOrToggleSong(mediaItem: com.example.spotifyfirebase.data.model.Music, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if(isPrepared && mediaItem.documentMusic ==
            curPlayingSong.value?.getString(MediaMetadata.METADATA_KEY_MEDIA_ID)) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if(toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.documentMusic, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe(Constants.MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}