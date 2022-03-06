package com.example.spotifyfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.rememberNavController
import com.example.spotifyfirebase.navigation.host.Host
import com.example.spotifyfirebase.ui.theme.SpotifyFirebaseTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotifyFirebaseTheme {
                Host(
                    navHostController = rememberNavController()
                )
            }
        }
    }
}
