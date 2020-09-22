package com.lonecoders.musicplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.models.Song

class SongsAdapter:
    ListAdapter<Song,SongsAdapter.SongListViewHolder>(DiffCallBack()) {
    class SongListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val albumInLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.model_song, parent, false)
        return SongListViewHolder(albumInLayout)
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.album_in_song_name).text =
            getItem(position).songName
        val songInfo =
            "${getItem(position).artistName} - ${getItem(position).albumName}"
        holder.itemView.findViewById<TextView>(R.id.song_info).text = songInfo

        Glide.with(holder.itemView)
            .load(getItem(position).songAlbumCoverUri)
            .placeholder(R.drawable.ic_song_cover_default)
            .into(holder.itemView.findViewById(R.id.album_cover_in_song))
    }

    class DiffCallBack : DiffUtil.ItemCallback<Song>(){
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }

    }
}


