package com.example.spotifyfirebase.screen.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.spotifyfirebase.api.model.playlist.Playlist
import com.example.spotifyfirebase.api.model.playlist.music.Genre
import com.example.spotifyfirebase.data.model.Music
import com.example.spotifyfirebase.exoplayer.resource.Resource
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRouteScreen
import com.example.spotifyfirebase.screen.homeScreen.view.PlaylistView
import com.example.spotifyfirebase.screen.homeScreen.viewModel.HomeViewModel
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    var timeText by remember { mutableStateOf("") }
    val music:MutableState<Resource<List<Music>>> = remember { mutableStateOf(Resource.loading(null)) }
    val playlist = remember { mutableStateOf(listOf<Playlist>()) }
    val genre = remember { mutableStateOf(listOf<Genre>()) }

    homeViewModel.getCurrentTimeText()
    homeViewModel.responseTimeText.onEach {
        timeText = it
    }.launchWhenStarted(lifecycleScope)

    homeViewModel.getPlaylist()
    homeViewModel.responsePlaylist.onEach {
        playlist.value = it
    }.launchWhenStarted(lifecycleScope)

    homeViewModel.getGenre()
    homeViewModel.responseGenre.onEach {
        genre.value = it
    }.launchWhenStarted(lifecycleScope)

    homeViewModel.mediaItems.onEach {
        music.value = it
    }.launchWhenStarted(lifecycleScope)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        Column {
            LazyColumn(content = {

                item {
                    Text(
                        text = timeText,
                        color = secondaryBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            start = 20.dp,
                            bottom = 5.dp,
                            top = 5.dp,
                            end = 5.dp
                        )
                    )
                }

                item {
                    LazyRow(content = {
                        music.value.data?.let {
                            items(it){ item ->
                                Card(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .clickable {
                                            homeViewModel.playOrToggleSong(
                                                mediaItem = item,
                                                toggle = false
                                            )
                                        },
                                    shape = AbsoluteRoundedCornerShape(10.dp)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        SubcomposeAsyncImage(
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(10.dp),
                                            model = item.iconUrl,
                                            contentDescription = null,
                                            loading = {
                                                val stateCoil = painter.state
                                                if (stateCoil is AsyncImagePainter.State.Loading
                                                    || stateCoil is AsyncImagePainter.State.Error) {
                                                    CircularProgressIndicator(
                                                        color = secondaryBackground
                                                    )
                                                } else {
                                                    SubcomposeAsyncImageContent()
                                                }
                                            }
                                        )

                                        Text(
                                            text = item.documentMusic,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }
                                }
                            }
                        }
                    })
                }

                item {
                    Column {
                        PlaylistView(
                            navController = navController,
                            playlist = playlist.value
                        )

                        Text(
                            text = "Genre",
                            color = secondaryBackground,
                            modifier = Modifier.padding(5.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            })

            LazyVerticalGrid(
                cells = GridCells.Adaptive(150.dp),
                content = {
                    items(genre.value){ item ->
                        Card(
                            modifier = Modifier
                                .padding(5.dp)
                                .clickable {
                                    navController.navigate(MusicRouteScreen.MusicGenre.dara(
                                        idGenre = item.id!!
                                    ))
                                },
                            shape = AbsoluteRoundedCornerShape(10.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                SubcomposeAsyncImage(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(10.dp),
                                    model = item.genreIcon,
                                    contentDescription = null,
                                    loading = {
                                        val stateCoil = painter.state
                                        if (stateCoil is AsyncImagePainter.State.Loading
                                            || stateCoil is AsyncImagePainter.State.Error) {
                                            CircularProgressIndicator(
                                                color = secondaryBackground
                                            )
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                )

                                Text(
                                    text = item.genreTitle,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}