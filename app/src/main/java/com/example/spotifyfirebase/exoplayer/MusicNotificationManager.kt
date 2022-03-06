package com.example.spotifyfirebase.exoplayer

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.spotifyfirebase.R
import com.example.spotifyfirebase.utils.Constants.NOTIFICATION_ID
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicNotificationManager(
    private val context: Context,
    sessionToken: MediaSessionCompat.Token,
    notificationListener:PlayerNotificationManager.NotificationListener,
    private val newSongCallback: () -> Unit
) {
    private val notificationManager:PlayerNotificationManager

    init {
        val mediaController = MediaControllerCompat(context, sessionToken)

        notificationManager = PlayerNotificationManager.Builder(
            context,
            NOTIFICATION_ID,
            "notification_channel_id"
        )
            .setSmallIconResourceId(R.drawable.ic_launcher_foreground)
            .setChannelNameResourceId(R.string.notification_channel_name)
            .setChannelDescriptionResourceId(R.string.notification_channel_description)
            .setNotificationListener(notificationListener)
            .setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            .build().apply {
                setMediaSessionToken(sessionToken)
            }
    }

    fun showNotification(player: Player){
        notificationManager.setPlayer(player)
    }


    private inner class DescriptionAdapter(
        private val mediaController: MediaControllerCompat
    ) : PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
            return mediaController.metadata.description.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return  mediaController.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence {
            return mediaController.metadata.description.subtitle.toString()
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback,
        ): Bitmap? {
            return null
//            val loading = ImageLoader(context)
//            val request = ImageRequest.Builder(context)
//                .data(mediaController.metadata.description.iconUri)
//                .build()
//            var result:Drawable? = null
//
//            CoroutineScope(Dispatchers.IO).launch {
//                result = (loading.execute(request) as SuccessResult).drawable
//            }
//            return (result as BitmapDrawable).bitmap
        }
    }
}