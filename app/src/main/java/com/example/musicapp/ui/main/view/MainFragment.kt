package com.example.musicapp.ui.main.view

import androidx.fragment.app.Fragment
import com.example.musicapp.databinding.MainFragmentBinding
import com.example.musicapp.ui.main.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(), IView {

    private lateinit var binding: MainFragmentBinding
    private val presenter = MainPresenter(this)


}
