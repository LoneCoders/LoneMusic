package com.lonecoders.musicplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.models.Song

class AlbumInAdapter(private val songsSet: Set<Song>) :
    RecyclerView.Adapter<AlbumInAdapter.SongListViewHolder>() {
    class SongListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val albumInLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.model_song, parent, false)
        return SongListViewHolder(albumInLayout)
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.album_in_song_name).text =
            songsSet.elementAt(position).songName
        val songInfo =
            "${songsSet.elementAt(position).artistName} - ${songsSet.elementAt(position).albumName}"
        holder.itemView.findViewById<TextView>(R.id.song_info).text = songInfo
        if (songsSet.elementAt(position).songAlbumCover != null)
            holder.itemView.findViewById<ImageView>(R.id.album_cover_in_song)
                .setImageBitmap(songsSet.elementAt(position).songAlbumCover)
    }

    override fun getItemCount(): Int {
        return songsSet.size
    }
}


