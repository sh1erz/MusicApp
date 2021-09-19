package com.example.musicapp.ui.main.view

import androidx.fragment.app.Fragment
import com.example.musicapp.databinding.MainFragmentBinding
import com.example.musicapp.ui.main.presenter.IPresenter
import com.example.musicapp.ui.main.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment(), IView {

    private lateinit var binding: MainFragmentBinding

    @Inject
    lateinit var presenter: IPresenter

}
