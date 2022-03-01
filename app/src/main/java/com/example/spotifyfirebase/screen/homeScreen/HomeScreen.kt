package com.example.spotifyfirebase.screen.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.spotifyfirebase.data.model.Music
import com.example.spotifyfirebase.screen.homeScreen.viewModel.HomeViewModel
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    var musics by remember { mutableStateOf(listOf<Music>()) }

    homeViewModel.getAllMusic()
    homeViewModel.responseAllMusic.onEach {
        musics = it
    }.launchWhenStarted(lifecycleScope)

    Column {
        Text(text = musics.toString())
    }
}