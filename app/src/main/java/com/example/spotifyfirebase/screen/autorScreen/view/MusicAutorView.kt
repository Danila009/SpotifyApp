package com.example.spotifyfirebase.screen.autorScreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRouteScreen
import com.example.spotifyfirebase.ui.theme.secondaryBackground

@Composable
fun MusicAutorView(
    navController:NavController,
    autorMusic:Music
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(MusicRouteScreen.MusicInfo.data(
                    musicId = autorMusic.id!!
                ))
            }
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp)
                .clickable {

                },
            model = autorMusic.webIcon,
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
                text = autorMusic.title,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = autorMusic.date,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
    Divider()
}