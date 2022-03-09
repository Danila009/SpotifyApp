package com.example.spotifyfirebase.screen.musicScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.spotifyfirebase.api.model.person.Autor
import com.example.spotifyfirebase.api.model.playlist.music.Genre
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRouteScreen
import com.example.spotifyfirebase.screen.musicScreen.view.musicInfo.AutorView
import com.example.spotifyfirebase.screen.musicScreen.view.musicInfo.GenreView
import com.example.spotifyfirebase.screen.musicScreen.viewModel.MusicViewModel
import com.example.spotifyfirebase.ui.theme.controlColor
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun MusicInfoScreen(
    musicViewModel: MusicViewModel = hiltViewModel(),
    navController: NavController,
    idMusic: Int,
) {
    val favoriteCheck = remember { mutableStateOf(false) }
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    var music by remember { mutableStateOf(Music()) }
    val musicGenre = remember { mutableStateOf(listOf<Genre>()) }
    val musicAutor = remember { mutableStateOf(listOf<Autor>()) }

    musicViewModel.getMusicItem(id = idMusic)
    musicViewModel.responseMusicItem.onEach {
        music = it
    }.launchWhenStarted(lifecycleScope)

    musicViewModel.getMusicGenre(idMusic)
    musicViewModel.responseMusicGenre.onEach {
        musicGenre.value = it
    }.launchWhenStarted(lifecycleScope)

    musicViewModel.getMusicAutor(idMusic)
    musicViewModel.responseMusicAutor.onEach {
        musicAutor.value = it
    }.launchWhenStarted(lifecycleScope)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn(content = {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .size(300.dp)
                                .padding(5.dp)
                                .clickable {
                                    navController.navigate(
                                        MusicRouteScreen.ImageZoomScreen.data(
                                            imageUrl = music.webIcon,
                                            musicId = idMusic
                                        )
                                    )
                                },
                            model = music.webIcon,
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

                        IconButton(onClick = { favoriteCheck.value != favoriteCheck.value }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = if (favoriteCheck.value) Color.Red else Color.Gray,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }

                    Text(
                        text = music.title,
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        color = secondaryBackground,
                        textAlign = TextAlign.Center
                    )
                }
                LazyRow(content = {
                    items(musicGenre.value){ item ->
                        Card(
                            modifier = Modifier.padding(5.dp),
                            shape = AbsoluteRoundedCornerShape(15.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                SubcomposeAsyncImage(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(5.dp),
                                    model = item.genreIcon,
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

                                Text(
                                    text = item.genreTitle,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                })

                LazyRow(content = {
                    items(musicAutor.value){ item ->
                        Card(
                            modifier = Modifier
                                .padding(5.dp)
                                .clickable {

                                },
                            shape = AbsoluteRoundedCornerShape(10.dp),
                            backgroundColor = controlColor
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                SubcomposeAsyncImage(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(5.dp),
                                    model = item.posterUrl,
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

                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                })

                Text(
                    text = music.date,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    color = secondaryBackground,
                    textAlign = TextAlign.End
                )
            }
        })
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn(content = {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .size(300.dp)
                                .padding(5.dp)
                                .clickable {
                                    navController.navigate(
                                        MusicRouteScreen.ImageZoomScreen.data(
                                            imageUrl = music.webIcon,
                                            musicId = idMusic
                                        )
                                    )
                                },
                            model = music.webIcon,
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

                        IconButton(onClick = { favoriteCheck.value != favoriteCheck.value }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = if (favoriteCheck.value) Color.Red else Color.Gray,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }

                    Text(
                        text = music.title,
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        color = secondaryBackground,
                        textAlign = TextAlign.Center
                    )
                }

                GenreView(
                    navController = navController,
                    genre = musicGenre.value
                )

                AutorView(
                    navController = navController,
                    autor = musicAutor.value,
                    musicId = idMusic
                )

                Text(
                    text = music.date,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    color = secondaryBackground,
                    textAlign = TextAlign.End
                )
            }
        })
    }
}