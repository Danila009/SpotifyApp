package com.example.spotifyfirebase.screen.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.spotifyfirebase.data.model.Music
import com.example.spotifyfirebase.screen.homeScreen.viewModel.HomeViewModel
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    var musics by remember { mutableStateOf(listOf<Music>()) }
    var timeText by remember { mutableStateOf("") }

    homeViewModel.getAllMusic()
    homeViewModel.responseAllMusic.onEach {
        musics = it
    }.launchWhenStarted(lifecycleScope)

    homeViewModel.getCurrentTimeText()
    homeViewModel.responseTimeText.onEach {
        timeText = it
    }.launchWhenStarted(lifecycleScope)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        Column {
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

            Text(
                text = musics.toString()
            )
        }
    }
}