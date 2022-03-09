package com.example.spotifyfirebase.screen.homeScreen.viewModel

import android.media.MediaMetadata.METADATA_KEY_MEDIA_ID
import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyfirebase.api.model.playlist.Playlist
import com.example.spotifyfirebase.api.model.playlist.music.Genre
import com.example.spotifyfirebase.api.repository.ApiRepository
import com.example.spotifyfirebase.data.model.Music
import com.example.spotifyfirebase.exoplayer.MusicServiceConnection
import com.example.spotifyfirebase.exoplayer.isPlayEnabled
import com.example.spotifyfirebase.exoplayer.isPlaying
import com.example.spotifyfirebase.exoplayer.isPrepared
import com.example.spotifyfirebase.exoplayer.resource.Resource
import com.example.spotifyfirebase.utils.Constants.MEDIA_ROOT_ID
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
    private val apiRepository: ApiRepository,
    private val musicServiceConnection: MusicServiceConnection
):ViewModel() {
    /**Api
     */
    private val _responseTimeText:MutableStateFlow<String> = MutableStateFlow("")
    val responseTimeText:StateFlow<String> = _responseTimeText.asStateFlow()
    private val _responsePlaylist:MutableStateFlow<List<Playlist>> = MutableStateFlow(listOf())
    val responsePlaylist:StateFlow<List<Playlist>> = _responsePlaylist.asStateFlow()
    private val _responseGenre:MutableStateFlow<List<Genre>> = MutableStateFlow(listOf())
    val responseGenre:StateFlow<List<Genre>> = _responseGenre.asStateFlow()

    /**
     * MusicServiceFirebase
     */
    private val _mediaItems:MutableStateFlow<Resource<List<Music>>> = MutableStateFlow(Resource.loading(
        listOf()))
    val mediaItems: StateFlow<Resource<List<Music>>> = _mediaItems.asStateFlow()

    private val isConnected = musicServiceConnection.isConnected
    private val networkError = musicServiceConnection.networkError
    private val curPlayingSong = musicServiceConnection.curPlayingSong
    private val playbackState = musicServiceConnection.playbackState

    init {
        _mediaItems.value = Resource.loading(null)
        musicServiceConnection.subscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(
                parentId: String,
                children: MutableList<MediaBrowserCompat.MediaItem>
            ) {
                super.onChildrenLoaded(parentId, children)
                val items = children.map {
                    Music(
                        documentMusic = it.mediaId!!,
                        musicUrl =  it.description.mediaUri.toString(),
                        iconUrl = it.description.iconUri.toString()
                    )
                }
                _mediaItems.value = Resource.success(items)
            }
        })
    }

    /**
     * Api
     */
    fun getPlaylist(){
        viewModelScope.launch {
            try {
                _responsePlaylist.value = apiRepository.getPlaylist().body()!!
            }catch (e:Exception){
                Log.e(TAG_RETROFIT, e.message.toString())
            }
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

    /**
     * MusicServiceFirebase
     */

    fun skipToNextSong() =
        musicServiceConnection.transportControls.skipToNext()

    fun skipToPreviousSong() =
        musicServiceConnection.transportControls.skipToPrevious()

    fun seekTo(pos: Long) =
        musicServiceConnection.transportControls.seekTo(pos)

    fun playOrToggleSong(mediaItem: Music, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if(isPrepared && mediaItem.documentMusic ==
            curPlayingSong.value?.getString(METADATA_KEY_MEDIA_ID)) {
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
        musicServiceConnection.unsubscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }

    /**
     * Unit
     */
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