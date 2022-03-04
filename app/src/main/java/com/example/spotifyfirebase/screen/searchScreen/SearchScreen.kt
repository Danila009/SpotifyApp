package com.example.spotifyfirebase.screen.searchScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.spotifyfirebase.screen.searchScreen.view.SearchView
import com.example.spotifyfirebase.screen.searchScreen.viewModel.SearchViewModel
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavController,
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val musics = remember { mutableStateOf(listOf<Music>()) }
    val search = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val state = rememberScaffoldState()

    searchViewModel.getMusic(
        search = search.value
    )
    searchViewModel.responseMusic.onEach {
        musics.value = it
    }.launchWhenStarted(lifecycleScope)

    Scaffold(
        scaffoldState = state,
        topBar = {
            TopAppBar(
                backgroundColor = primaryBackground,
                elevation = 12.dp,
                title = {
                    SearchView(
                        search = search
                    )
                }, navigationIcon = {
                    IconButton(onClick = { scope.launch {state.drawerState.open() }}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = secondaryBackground
                        )
                    }
                }
            )
        },drawerBackgroundColor = primaryBackground,
        drawerContent = {

        }, content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = primaryBackground
            ) {
                LazyColumn(content = {
                    items(musics.value){ item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(MusicRouteScreen.MusicInfo.data(
                                        musicId = item.id!!
                                    ))
                                }
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(10.dp)
                                    .clickable {

                                    },
                                model = item.webIcon,
                                contentDescription = null,
                                loading = {
                                    val stateCoil = painter.state
                                    if (stateCoil is AsyncImagePainter.State.Loading || stateCoil is AsyncImagePainter.State.Error) {
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
                })
            }
        }
    )
}