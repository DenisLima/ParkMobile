package com.djv.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.djv.domain.model.Music
import com.djv.presentation.adapter.MusicAdapter
import com.djv.presentation.adapter.MusicClickListener
import com.djv.presentation.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity: AppCompatActivity(), MusicClickListener {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private val musicAdapter = MusicAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initComponents()
        prepareObservers()
    }

    private fun initComponents() {
        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = musicAdapter
        }

        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    viewModel.initEvent(HomeViewModel.HomeEvent.InputSearchKey(s.length))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //
            }

        })

        binding.searchButton.setOnClickListener {
            val searchText = binding.searchText.text.toString()
            viewModel.initEvent(HomeViewModel.HomeEvent.GetMusicList(searchText))
        }
    }

    private fun prepareObservers() {
        viewModel.getViewState().observe(this) {
            when(it) {
                is HomeViewModel.HomeViewState.ErrorMessage -> showErrorMessage(it.errorMessage)
                HomeViewModel.HomeViewState.HiddenLoading -> hiddenLoading()
                is HomeViewModel.HomeViewState.LoadedMusicList -> setMusicList(it.musicList)
                HomeViewModel.HomeViewState.ShowLoading -> showLoading()
                HomeViewModel.HomeViewState.DisableSearchButton -> disableSearchButton()
                HomeViewModel.HomeViewState.EnableSearchButton -> enableSearchButton()
            }
        }
    }

    private fun setMusicList(list: List<Music>) {
        hiddenKeyboard()
        hiddenLoading()
        binding.recyclerView.apply {
            musicAdapter.setList(list)
        }
    }

    private fun hiddenKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.searchText.windowToken, 0)
    }

    private fun enableSearchButton() {
        binding.searchButton.isEnabled = true
    }

    private fun disableSearchButton() {
        binding.searchButton.isEnabled = false
    }

    private fun showLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hiddenLoading() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        hiddenLoading()
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun onClick(music: Music) {
        Toast.makeText(applicationContext, "Aqui veio ${music.artistName}", Toast.LENGTH_LONG).show()
    }
}