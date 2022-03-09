package com.example.spotifyfirebase.screen.searchScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.api.model.user.HistorySearch
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRouteScreen
import com.example.spotifyfirebase.screen.searchScreen.view.SearchView
import com.example.spotifyfirebase.screen.searchScreen.viewModel.SearchViewModel
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.delay
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
    val checkSearch = remember { mutableStateOf(true) }
    val historySearch = remember { mutableStateOf(listOf<HistorySearch>()) }

    val scope = rememberCoroutineScope()
    val state = rememberScaffoldState()

    searchViewModel.getMusic(
        search = search.value
    )
    searchViewModel.responseMusic.onEach {
        musics.value = it
    }.launchWhenStarted(lifecycleScope)

    searchViewModel.getHistorySearch()
    searchViewModel.responseHistorySearch.onEach {
        historySearch.value = it
    }.launchWhenStarted(lifecycleScope)

    LaunchedEffect(key1 = search.value, block = {
        delay(1500L)
        if (search.value.isNotEmpty()){
            searchViewModel.postHistorySearch(
                HistorySearch(
                    title = search.value
                )
            )
        }
    })

    Scaffold(
        scaffoldState = state,
        topBar = {
            TopAppBar(
                backgroundColor = primaryBackground,
                elevation = 12.dp,
                title = {
                    SearchView(
                        search = search,
                        onClick = {
                            checkSearch.value = false
                        }
                    ){
                        checkSearch.value = true
                    }
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
            Text(
                text = "Sorting",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = secondaryBackground,
                fontSize = 20.sp
            )

        }, content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = primaryBackground
            ) {
                if (!checkSearch.value){
                    LazyColumn(content = {
                        items(historySearch.value){ item ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        search.value = item.title
                                        checkSearch.value = true
                                    }){
                                Text(
                                    text = item.title,
                                    modifier = Modifier.padding(5.dp)
                                )
                                Divider()
                            }
                        }
                    })
                }else{
                    LazyColumn(content = {
                        items(musics.value){ item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(
                                            MusicRouteScreen.MusicInfo.data(
                                                musicId = item.id!!
                                            )
                                        )
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
        }
    )
}