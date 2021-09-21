package com.example.musicapp.ui.details

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.musicapp.ui.details.TrackFragment.Companion.MUSIC_URI
import com.example.musicapp.ui.main.model.LOG
import java.io.IOException


class AudioPlayerService : Service() {
    private lateinit var player: MediaPlayer
    private val binder = AudioPlayerBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initMediaPlayer(
            intent?.getStringExtra(MUSIC_URI)
                ?: throw IllegalArgumentException("No uri was passed to service")
        )
        return super.onStartCommand(intent, flags, startId)
    }

    fun play() {
        Log.i(LOG, "play")
        try {
            player.start()
        } catch (ex: UninitializedPropertyAccessException) {
            Log.i(LOG, "play: Player has not been initialised")
        }
    }

    fun stop() {
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
                setDataSource(url)
            } catch (ex: IllegalArgumentException) {
                Log.i(LOG, "initMediaPlayer: ${ex.message}")
            } catch (ex: IOException) {
                Log.i(LOG, "initMediaPlayer: ${ex.message}")
            }
            prepare()
            start()
        }
    }

    inner class AudioPlayerBinder : Binder() {
        fun getService() = this@AudioPlayerService
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(LOG, "onCreate: service created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG, "onDestroy: service destroyed")
    }
}