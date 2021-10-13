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
import androidx.transition.TransitionInflater
import com.example.data.entities.Track
import com.example.musicapp.LOG
import com.example.musicapp.R
import com.example.data_android.MusicRepository
import com.example.musicapp.databinding.FragmentTrackBinding
import com.example.musicapp.services.AudioPlayerService
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TrackFragment : Fragment() {

    private var _binding: FragmentTrackBinding? = null
    private val binding get() = _binding!!
    private var audioService: AudioPlayerService? = null
    @Inject
    lateinit var repo: MusicRepository

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
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
            CoroutineScope(Dispatchers.IO).launch { repo.addTrackUpIfExists(track) }
            binding.apply {
                imgTrack.transitionName = track.album.cover
                Picasso.with(context)
                    .load(track.album.cover_xl)
                    .into(imgTrack)
                tvTrackTitle.text = track.title
                tvTrackArtist.text = track.artist.name
                bPlay.setOnClickListener(buttonListener)

                val intent = Intent(activity, AudioPlayerService::class.java).also {
                    it.putExtra(TRACK, track)
                    it.action = AudioPlayerService.ACTION_START_SERVICE
                }
                if (savedInstanceState == null) {
                    activity?.startService(intent)
                }
                activity?.bindService(
                    intent,
                    connection,
                    Context.BIND_AUTO_CREATE
                )
            }

        }
    }

    private val buttonListener = object : View.OnClickListener {
        private var isPlaying = true
        override fun onClick(v: View?) {
            if (isPlaying) {
                isPlaying = false
                audioService?.pause()
                (v as ImageButton).setImageResource(R.drawable.ic_baseline_play_arrow_24)
            } else {
                isPlaying = true
                audioService?.play()
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
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG, "track fragm onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG, "trackFragment onDestroy")
        audioService = null
        activity?.unbindService(connection)

    }


}