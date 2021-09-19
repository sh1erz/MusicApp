package com.example.musicapp.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicapp.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

}
