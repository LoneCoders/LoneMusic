package com.lonecoders.musicplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.models.Album

class AlbumAdapter(
    private val onClickListener: CustomOnClickListener
) :
    ListAdapter<Album,AlbumAdapter.AlbumListViewHolder>(DiffCallback()) {
    class AlbumListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val albumLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_album, parent, false)
        return AlbumListViewHolder(albumLayout)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.album_name).text =
            getItem(position).albumName

        Glide.with(holder.itemView)
            .load(getItem(position).albumCoverUri)
            .placeholder(R.drawable.ic_album_cover_default)
            .into(holder.itemView.findViewById<ImageView>(R.id.album_cover))

        holder.itemView.setOnClickListener {
            onClickListener.onClick(getItem(position))
        }
    }



}

class DiffCallback : DiffUtil.ItemCallback<Album>(){
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }

}

class CustomOnClickListener(val clickListener: (album: Album) -> Unit) {
    fun onClick(album: Album) = clickListener(album)
}
