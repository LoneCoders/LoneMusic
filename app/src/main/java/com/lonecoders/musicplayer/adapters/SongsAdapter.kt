package com.lonecoders.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.databinding.ModelSongBinding
import com.lonecoders.musicplayer.models.Song

class SongsAdapter(private val clickListener: SongsClickListener) :
    ListAdapter<Song, SongsAdapter.SongListViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        return SongListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class SongListViewHolder(val binding: ModelSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): SongListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ModelSongBinding.inflate(layoutInflater, parent, false)
                return SongListViewHolder(binding)
            }
        }

        fun bind(song: Song, clickListener: SongsClickListener) {
            binding.song = song
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem == newItem
    }
}

class SongsClickListener(val clickListener: (song: Song) -> Unit) {
    fun onClick(song: Song) = clickListener(song)
}


