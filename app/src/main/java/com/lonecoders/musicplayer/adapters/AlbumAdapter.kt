package com.lonecoders.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.databinding.ModelAlbumBinding
import com.lonecoders.musicplayer.models.Album

class AlbumAdapter(private val clickListener: AlbumClickListener) :
    ListAdapter<Album, AlbumAdapter.AlbumListViewHolder>(DiffAlbumCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        return AlbumListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class AlbumListViewHolder(private val binding: ModelAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): AlbumListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ModelAlbumBinding.inflate(layoutInflater, parent, false)
                return AlbumListViewHolder(binding)
            }
        }

        fun bind(album: Album, clickListener: AlbumClickListener) {
            binding.album = album
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

class DiffAlbumCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }

}

class AlbumClickListener(val clickListener: (album: Album) -> Unit) {
    fun onClick(album: Album) = clickListener(album)
}
