package com.example.musicapp.ui.details

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.musicapp.MainActivity
import com.example.musicapp.R
import com.example.musicapp.ui.details.TrackFragment.Companion.TRACK_TITLE
import com.example.musicapp.ui.details.TrackFragment.Companion.TRACK_URI
import com.example.musicapp.ui.main.model.LOG
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
                val uri = intent.getStringExtra(TRACK_URI)
                    ?: throw IllegalArgumentException("No uri was passed to service")
                initMediaPlayer(uri)
                val title = intent.getStringExtra(TRACK_TITLE)
                    ?: throw IllegalArgumentException("No title was passed to service")
                displayForegroundNotification(title)
            }
            ACTION_PLAY -> play()
            ACTION_PAUSE -> pause()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channelId = "audio_service"
        val channelName = "Music service"
        NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH).also {
            it.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(it)
        }
        return channelId
    }

    private fun displayForegroundNotification(title: String) {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        } else ""
        val returnIntent =Intent(
            this,
            MainActivity::class.java
        ).apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val pendingIntent =
            PendingIntent.getActivity(
                this, 0, returnIntent , PendingIntent.FLAG_UPDATE_CURRENT
            )
        // play button intent
        val playIntent = Intent(this, this::class.java).apply {
            action = ACTION_PLAY
        }
        val pendingPlayIntent =
            PendingIntent.getService(this,0,playIntent,0)
        val playAction = NotificationCompat
            .Action(android.R.drawable.ic_media_play, "play", pendingPlayIntent)

        //pause button intent
        val pauseIntent = Intent(this, this::class.java).apply {
            action = ACTION_PAUSE
        }
        val pendingPauseIntent =
            PendingIntent.getService(this,0,pauseIntent,0)
        val pauseAction = NotificationCompat.Action(android.R.drawable.ic_media_pause, "pause", pendingPauseIntent)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .addAction(pauseAction)
            .addAction(playAction)
            .setWhen(0)
            .build()
        startForeground(1001, notification)
        //todo img

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
        player = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            try {
                //todo  network issues
                setDataSource(url)
            } catch (ex: IllegalArgumentException) {
                Log.i(LOG, "initMediaPlayer: ${ex.message}")
            } catch (ex: IOException) {
                Log.i(LOG, "initMediaPlayer: ${ex.message}")
            }
            prepare()
            start()
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