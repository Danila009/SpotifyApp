package com.example.spotifyfirebase.screen.musicScreen.view.musicInfo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.spotifyfirebase.api.model.person.Autor
import com.example.spotifyfirebase.api.model.playlist.music.Music
import com.example.spotifyfirebase.navigation.navGraph.autorNavGraph.constants.AutorRouteScreen
import com.example.spotifyfirebase.ui.theme.controlColor
import com.example.spotifyfirebase.ui.theme.secondaryBackground

@Composable
fun AutorView(
    autor:List<Autor>,
    navController: NavController,
    musicId:Int
) {
    LazyRow(content = {
        items(autor){ item ->
            Card(
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        navController.navigate(AutorRouteScreen.AutorInfo.date(
                            autorId = item.id!!,
                            musicId = musicId
                        ))
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
}