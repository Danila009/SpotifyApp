package com.example.spotifyfirebase.screen.musicScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRouteScreen
import com.example.spotifyfirebase.screen.musicScreen.viewModel.MusicViewModel
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun MusicPlaylistScreen(
    musicViewModel: MusicViewModel = hiltViewModel(),
    navController: NavController,
    idPlaylist: Int,
) {
    val playlistItem = remember { mutableStateOf(listOf<Music>()) }
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    
    musicViewModel.getPlaylistItem(id = idPlaylist)
    musicViewModel.responsePlaylistItem.onEach { 
        playlistItem.value = it
    }.launchWhenStarted(lifecycleScope)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn(content = {
            items(playlistItem.value){ item ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(MusicRouteScreen.MusicInfo.data(
                                    musicId = item.id!!
                                ))
                            },
                    ){
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .size(100.dp)
                                .clickable {

                                }
                                .padding(10.dp),
                            model = item.webIcon,
                            contentDescription = null,
                            loading = {
                                val state = painter.state
                                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                    CircularProgressIndicator(
                                        color = secondaryBackground
                                    )
                                } else {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                        )

                        Column {
                            Text(
                                text = item.title,
                                modifier = Modifier.padding(10.dp)
                            )

                            Text(
                                text = item.date,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                    Divider()
                }
            }
        })
    }
}