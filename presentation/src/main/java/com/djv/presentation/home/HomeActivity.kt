package com.djv.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.djv.presentation.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initComponents()
    }

    private fun initComponents() {
        viewModel.initEvent(HomeViewModel.HomeEvent.GetMusicList("lady"))
    }
}