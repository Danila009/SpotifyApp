package com.example.spotifyfirebase.screen.homeScreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.spotifyfirebase.api.model.playlist.Playlist
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRouteScreen
import com.example.spotifyfirebase.ui.theme.secondaryBackground

@Composable
fun PlaylistView(
    navController: NavController,
    playlist:List<Playlist>
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Playlist",
            modifier = Modifier.padding(5.dp),
            fontWeight = FontWeight.Bold,
            color = secondaryBackground
        )

        TextButton(
            modifier = Modifier.padding(5.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Все ->",
                fontWeight = FontWeight.Bold,
                color = secondaryBackground
            )
        }
    }

    LazyRow(content = {
        items(playlist){ item ->
            Column(
                modifier = Modifier
                    .size(130.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(MusicRouteScreen.MusicPlaylist.data(
                            playlistId = item.id!!
                        ))
                    }, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.size(100.dp),
                    model = item.icon,
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
                    text = item.title,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    })
}