package com.example.spotifyfirebase.screen.searchScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spotifyfirebase.screen.searchScreen.view.SearchView
import com.example.spotifyfirebase.ui.theme.primaryBackground

@Composable
fun SearchScreen(
    navController: NavController
) {
    val search = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = primaryBackground,
                elevation = 12.dp,
                title = {
                    SearchView(
                        search = search
                    )
                }
            )
        }, content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = primaryBackground
            ) {

            }
        }
    )
}