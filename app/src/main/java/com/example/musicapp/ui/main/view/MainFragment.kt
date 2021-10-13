package com.example.musicapp.ui.main.view

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.musicapp.LOG
import com.example.musicapp.R
import com.example.musicapp.databinding.MainFragmentBinding
import com.example.musicapp.ui.details.TrackFragment
import com.example.musicapp.ui.main.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(), TrackView {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val presenter: MainPresenter by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(LOG, "onViewCreated: MainFragment")
        presenter.attachView(this, lifecycle)
        presenter.loadTracks()
    }

    override fun showList() {
        binding.recyclerTracks.adapter = TrackAdapter(presenter)
    }

    override fun showDetails(parcelable: Parcelable, extras: FragmentNavigator.Extras?) {
        findNavController()
            .navigate(
                R.id.action_main_to_trackDetails,
                bundleOf(TrackFragment.TRACK to parcelable),
                null,
                extras
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
