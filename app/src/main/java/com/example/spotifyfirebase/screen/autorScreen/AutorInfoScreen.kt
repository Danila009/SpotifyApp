package com.example.spotifyfirebase.screen.autorScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.spotifyfirebase.api.model.person.Autor
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRouteScreen
import com.example.spotifyfirebase.screen.autorScreen.view.MusicAutorView
import com.example.spotifyfirebase.screen.autorScreen.view.PlaylistAutorView
import com.example.spotifyfirebase.screen.autorScreen.viewModel.AutorViewModel
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun AutorInfoScreen(
    autorViewModel: AutorViewModel = hiltViewModel(),
    navController: NavController,
    idAutor: Int,
    musicId:Int
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    val autor = remember { mutableStateOf(Autor()) }
    val autorMusic = remember { mutableStateOf(listOf<Music>()) }

    autorViewModel.getAutorItem(id = idAutor)
    autorViewModel.responseAutorItem.onEach {
        autor.value = it
    }.launchWhenStarted(lifecycleScope)

    autorViewModel.getAutorMusic(id = idAutor)
    autorViewModel.responseAutorMusic.onEach {
        autorMusic.value = it
    }.launchWhenStarted(lifecycleScope)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = primaryBackground,
                elevation = 8.dp,
                title = {
                    Text(text = autor.value.name)
                }, navigationIcon = {
                    IconButton(onClick = { navController.navigate(MusicRouteScreen.MusicInfo.data(
                        musicId = musicId
                    )) }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                }
            )
        }, content = {
            Surface(
                color = primaryBackground,
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(content = {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier
                                    .size(300.dp)
                                    .padding(5.dp)
                                    .clickable {

                                    },
                                model = autor.value.posterUrl,
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
                                text = autor.value.name,
                                modifier = Modifier.padding(5.dp),
                                color = secondaryBackground,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        PlaylistAutorView(
                            navController = navController,
                            autor = autor.value
                        )
                    }

                    item {
                        Text(
                            text = "Music",
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            color = secondaryBackground,
                            textAlign = TextAlign.Center
                        )
                    }

                    items(autorMusic.value){ item ->
                        MusicAutorView(
                            navController = navController,
                            autorMusic = item
                        )
                    }

                    item {
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                        )
                    }
                })
            }
        }
    )
}