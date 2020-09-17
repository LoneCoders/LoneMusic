package com.lonecoders.musicplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.transaction
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.fragments.AlbumInFragment
import com.lonecoders.musicplayer.models.Album

class AlbumAdapter(private val fm: FragmentManager, private val albumSet: Set<Album>) :
    RecyclerView.Adapter<AlbumAdapter.AlbumListViewHolder>() {
    class AlbumListViewHolder(itemView: View, private val albumSet: Set<Album>) :
        RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val albumLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_album, parent, false)
        return AlbumListViewHolder(albumLayout, albumSet)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.album_name).text =
            albumSet.elementAt(position).albumName
        holder.itemView.setOnClickListener {
            val fragment = AlbumInFragment(albumSet.elementAt(position).albumSongs)
            fm.beginTransaction().replace(R.id.album_list,fragment).commit()
        }
    }

    override fun getItemCount(): Int {
        return albumSet.size
    }

}