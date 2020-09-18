package com.lonecoders.musicplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.models.Album

class AlbumAdapter(
    private val albumSet: Set<Album>,
    private val onClickListener: CustomOnClickListener
) :
    RecyclerView.Adapter<AlbumAdapter.AlbumListViewHolder>() {
    class AlbumListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val albumLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_album, parent, false)
        return AlbumListViewHolder(albumLayout)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.album_name).text =
            albumSet.elementAt(position).albumName

        Glide.with(holder.itemView)
            .load(albumSet.elementAt(position).albumCoverUri)
            .placeholder(R.drawable.ic_album_cover_default)
            .into(holder.itemView.findViewById(R.id.album_cover))

        holder.itemView.setOnClickListener {
            onClickListener.onClick(albumSet.elementAt(position))
        }
    }

    override fun getItemCount(): Int {
        return albumSet.size
    }

}

class CustomOnClickListener(val clickListener: (album: Album) -> Unit) {
    fun onClick(album: Album) = clickListener(album)
}
