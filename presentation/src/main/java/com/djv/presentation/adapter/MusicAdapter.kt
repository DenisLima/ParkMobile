package com.djv.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.djv.domain.model.Music
import com.djv.presentation.databinding.ItemListBinding

class MusicAdapter(
    private val musicClickListener: MusicClickListener
): RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    private var musicList: List<Music> = listOf()
    inner class MusicViewHolder(val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root)

    fun setList(list: List<Music>) {
        this.musicList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val listItem: Music = musicList[position]
        holder.binding.artistName.text = listItem.artistName
        holder.binding.trackerName.text = listItem.trackName

        Glide.with(holder.itemView.context)
            .load(listItem.pictureUrl)
            .into(holder.binding.imageDisc)

        holder.binding.rootCard.setOnClickListener {
            musicClickListener.onClick(listItem)
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}

interface MusicClickListener {

    fun onClick(music: Music)
}