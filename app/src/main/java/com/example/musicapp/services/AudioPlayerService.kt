package com.example.musicapp.services

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.data.entities.Track
import com.example.musicapp.LOG
import com.example.musicapp.MainActivity
import com.example.musicapp.R
import com.example.musicapp.services.NotificationUtil.createNotificationChannel
import com.example.musicapp.ui.details.TrackFragment.Companion.TRACK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException


class AudioPlayerService : Service() {
    private lateinit var player: MediaPlayer
    private val binder = AudioPlayerBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) return super.onStartCommand(intent, flags, startId)
        when (intent.action) {
            ACTION_START_SERVICE -> {
                val track = intent.getParcelableExtra<Track>(TRACK)
                    ?: throw IllegalArgumentException("No track was passed to service")
                initMediaPlayer(track.musicUri)
                displayForegroundNotification(track)
            }
            ACTION_PLAY -> play()
            ACTION_PAUSE -> pause()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun displayForegroundNotification(track: Track) {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(this, "audio_service", "Audio Player")
        } else ""

        //go to app intent
        val returnIntent = Intent(
            this,
            MainActivity::class.java
        ).apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val pendingIntent =
            PendingIntent.getActivity(
                this, 0, returnIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

        // play button intent
        val playIntent = Intent(this, this::class.java).apply {
            action = ACTION_PLAY
        }
        val pendingPlayIntent =
            PendingIntent.getService(this, 0, playIntent, 0)
        val playAction = NotificationCompat
            .Action(android.R.drawable.ic_media_play, "Play", pendingPlayIntent)

        //pause button intent
        val pauseIntent = Intent(this, this::class.java).apply {
            action = ACTION_PAUSE
        }
        val pendingPauseIntent =
            PendingIntent.getService(this, 0, pauseIntent, 0)
        val pauseAction = NotificationCompat.Action(
            android.R.drawable.ic_media_pause,
            "Pause",
            pendingPauseIntent
        )

        val notification = NotificationCompat.Builder(this@AudioPlayerService, channelId)
            .setContentTitle(track.title)
            .setContentText(track.artist.name)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .addAction(pauseAction)
            .addAction(playAction)
            .setWhen(0)


        CoroutineScope(Dispatchers.IO).launch {
            Glide.with(this@AudioPlayerService)
                .asBitmap()
                .load(track.album.cover)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        notification.setLargeIcon(resource)
                        startForeground(1001, notification.build())
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

        }
    }


    fun play() {
        Log.i(LOG, "play")
        try {
            player.start()
        } catch (ex: UninitializedPropertyAccessException) {
            Log.i(LOG, "play: Player has not been initialised")
        }
    }

    fun pause() {
        try {
            player.pause()
        } catch (ex: UninitializedPropertyAccessException) {
            Log.i(LOG, "play: Player has not been initialised")
        }
        Log.i(LOG, "stop")
    }


    private fun initMediaPlayer(url: String) {
        try {
            player.release()
        } catch (ex: UninitializedPropertyAccessException) {
            Log.i(LOG, "player has not been initialized before")
        }

        player = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            try {
                setDataSource(url)
            } catch (ex: IllegalArgumentException) {
                Log.i(LOG, "initMediaPlayer: ${ex.message}")
            } catch (ex: IOException) {
                Log.i(LOG, "initMediaPlayer: ${ex.message}")
            }
            prepare()
            start()
            setOnErrorListener { mp, what, extra ->
                Log.i(LOG, "MEDIAPLAYER ERR: $what")
                false
            }
            setOnCompletionListener {
                stopForeground(true)
                stopSelf()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
        Log.i(LOG, "onDestroy: service destroyed")
    }

    inner class AudioPlayerBinder : Binder() {
        fun getService() = this@AudioPlayerService
    }

    companion object {
        const val ACTION_START_SERVICE = "ACTION_START_FOREGROUND_SERVICE"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_PLAY = "ACTION_PLAY"
    }
}