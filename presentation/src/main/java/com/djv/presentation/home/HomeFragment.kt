package com.djv.presentation.home

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.djv.domain.model.Music
import com.djv.presentation.adapter.MusicAdapter
import com.djv.presentation.adapter.MusicClickListener
import com.djv.presentation.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(), MusicClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()
    private val musicAdapter = MusicAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        viewModel.getViewState().observe(viewLifecycleOwner) {
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
        val inputMethodManager = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
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
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onClick(music: Music) {
        Toast.makeText(context, "Aqui veio ${music.artistName}", Toast.LENGTH_LONG).show()
    }
}