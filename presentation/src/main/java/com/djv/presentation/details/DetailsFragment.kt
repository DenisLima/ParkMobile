package com.djv.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.djv.domain.model.Music
import com.djv.presentation.R
import com.djv.presentation.databinding.FragmentDetailsBinding
import com.djv.presentation.home.HomeFragment.Companion.MUSIC_ARGS

class DetailsFragment: Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val musicArgs by lazy {
        arguments?.getParcelable<Music>(MUSIC_ARGS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
    }

    private fun initComponents() {
        musicArgs?.let {
            binding.title.text = "${context?.getString(R.string.details_artist_name)} ${it.artistName}"
            binding.trackName.text = "${context?.getString(R.string.details_track_name)} ${it.trackName}"
            binding.collectionName.text = "${context?.getString(R.string.details_collection_name)} ${it.collectionName}"
            binding.collectionPrice.text = it.collectionPrice
            binding.collectionCurrency.text = it.currency
            binding.collectionCurrencyTrack.text = it.currency
            binding.trackPrice.text = it.trackPrice

            context?.let { it1 ->
                Glide.with(it1)
                    .load(it.pictureUrl)
                    .into(binding.image)
            }
        }
    }
}