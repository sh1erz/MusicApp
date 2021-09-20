package com.example.musicapp.ui.main.view

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicapp.R
import com.example.musicapp.databinding.MainFragmentBinding
import com.example.musicapp.ui.details.TrackFragment
import com.example.musicapp.ui.main.model.LOG
import com.example.musicapp.ui.main.model.MainViewModel
import com.example.musicapp.ui.main.presenter.IPresenter
import com.example.musicapp.ui.main.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(), IView {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: IPresenter

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
        presenter = MainPresenter(this, ViewModelProvider(this).get(MainViewModel::class.java))
        Log.i(LOG, "onViewCreated: MainFragment")
    }

    override fun showList() {
        binding.recyclerTracks.adapter = TrackAdapter(presenter)
    }

    override fun showDetails(parcelable: Parcelable) {
        findNavController()
            .navigate(R.id.action_main_to_trackDetails, bundleOf(TrackFragment.TRACK to parcelable))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
