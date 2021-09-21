package com.example.musicapp.ui.details

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.musicapp.R
import com.example.musicapp.data.entities.Track
import com.example.musicapp.databinding.FragmentTrackBinding
import com.example.musicapp.ui.main.model.LOG
import com.squareup.picasso.Picasso


class TrackFragment : Fragment() {

    private var _binding: FragmentTrackBinding? = null
    private val binding get() = _binding!!
    private lateinit var audioService: AudioPlayerService

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i(LOG, "onServiceConnected")
            val binder = service as AudioPlayerService.AudioPlayerBinder
            audioService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(LOG, "onServiceDisconnected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //passed from recycler with history or search
        arguments?.getParcelable<Track>(TRACK)?.let { track ->
            binding.apply {
                Picasso.with(context)
                    .load(track.album.cover_xl)
                    .into(imgTrack)
                tvTrackTitle.text = track.title
                tvTrackArtist.text = track.artist.name
                bPlay.setOnClickListener(buttonListener)
                initService(track.musicUri)
            }

        }

    }

    private val buttonListener = object : View.OnClickListener {
        private var isPlaying = true
        override fun onClick(v: View?) {
            if (isPlaying) {
                isPlaying = false
                audioService.stop()
                (v as ImageButton).setImageResource(R.drawable.ic_baseline_play_arrow_24)
            } else {
                isPlaying = true
                audioService.play()
                (v as ImageButton).setImageResource(R.drawable.ic_baseline_pause_24)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TRACK = "track"
        const val MUSIC_URI = "music_uri"
    }

    private fun initService(uri: String) {
        Intent(activity, AudioPlayerService::class.java).also {
            it.putExtra(MUSIC_URI, uri)
            activity?.bindService(
                it,
                connection,
                Context.BIND_AUTO_CREATE
            )
            activity?.startService(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(LOG, "trackFragment onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG, "trackFragment onDestroy")

        activity?.unbindService(connection)

    }


}