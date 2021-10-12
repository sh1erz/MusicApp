package com.example.musicapp.workmanager

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.hilt.work.HiltWorker
import androidx.navigation.NavDeepLinkBuilder
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.entities.Track
import com.example.data_android.MusicRepository
import com.example.musicapp.LOG
import com.example.musicapp.R
import com.example.musicapp.services.NotificationUtil
import com.example.musicapp.ui.details.TrackFragment
import com.squareup.picasso.Picasso
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class FindReleaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: MusicRepository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork()
            : Result {
        Log.i(LOG, "doWork: starts")

            try {
                val genreId: String =
                    PreferenceManager.getDefaultSharedPreferences(applicationContext)
                        .getString("release_genre", "152")!!
                val track = repository.getLatestReleaseTrackByGenreId(genreId.toInt())
                    ?: return Result.success()
                createReleaseNotification(track)
                return Result.success()
            } catch (ex: Exception) {
                Log.i(LOG, "doWork: ${ex.message} ${ex.stackTrace}")
                return Result.failure()
            }


    }

    private fun createReleaseNotification(track: Track) {
        Log.i(LOG, "createReleaseNotification: start")
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationUtil.createNotificationChannel(
                applicationContext,
                "release_worker",
                "Releases"
            )
        else ""

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.trackFragment)
            .setArguments(bundleOf(TrackFragment.TRACK to track))
            .createPendingIntent()

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Check out new release of ${track.artist.name}!")
            .setContentText(track.title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setWhen(0)

        val bmp = Picasso.with(applicationContext).load(track.album.cover).get()
        notification.setLargeIcon(bmp)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(584, notification.build())
        }
        Log.i(LOG, "createReleaseNotification: end")
    }


}