package com.example.spotifyfirebase.screen.musicScreen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.spotifyfirebase.navigation.navGraph.musicNavGraph.constants.MusicRouteScreen
import com.example.spotifyfirebase.ui.theme.controlColor
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters

@Composable
fun ImageZoomScreen(
    navController: NavController,
    imageUrl:String,
    idMusic:Int
) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = controlColor,
                title = {
                      Text(text = Converters.replaceRange(imageUrl, 30))
                }, navigationIcon = {
                    IconButton(onClick = { navController.navigate(
                        MusicRouteScreen.MusicInfo.data(
                            musicId = idMusic
                        )
                    ){
                        popUpTo(MusicRouteScreen.MusicInfo.data(
                            musicId = idMusic
                        )){
                            inclusive = true
                        }
                    } }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = secondaryBackground
                        )
                    }
                }
            )
        }, content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = primaryBackground
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            rotationZ = rotation,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .transformable(state = state)
                        .pointerInput(Unit){
                            detectTapGestures(onDoubleTap = {
                                scale * 2
                            })
                        },
                    model = imageUrl,
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
            }
        }
    )
}