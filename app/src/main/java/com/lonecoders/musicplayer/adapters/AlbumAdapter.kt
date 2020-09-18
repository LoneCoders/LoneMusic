package com.lonecoders.musicplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.transaction
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.fragments.AlbumInFragment
import com.lonecoders.musicplayer.models.Album

class AlbumAdapter(private val fm: FragmentManager, private val albumSet: Set<Album>,val onClickListener: onClickListener) :
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

        if(albumSet.elementAt(position).albumCover != null)
            holder.itemView.findViewById<ImageView>(R.id.album_cover).setImageBitmap(albumSet.elementAt(position).albumCover)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(albumSet.elementAt(position))
        }
    }

    override fun getItemCount(): Int {
        return albumSet.size
    }

}
class onClickListener (val clickListener : (album:Album) -> Unit){
    fun onClick(album : Album) = clickListener(album)
}
