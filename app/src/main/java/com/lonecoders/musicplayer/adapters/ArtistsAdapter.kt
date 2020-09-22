package com.lonecoders.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.databinding.ModelArtistsBinding
import com.lonecoders.musicplayer.models.Artists

class ViewHolder(val binding : ModelArtistsBinding) : RecyclerView.ViewHolder(binding.root)

class ArtistsAdapter(val onClickistener: CustomOnArtistListener) : ListAdapter<Artists,ViewHolder>(Diffcallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ModelArtistsBinding>(
            LayoutInflater.from(parent.context), R.layout.model_artists,parent,false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.artistsName.text = getItem(position).artistName
        holder.itemView.setOnClickListener {
            onClickistener.onClick(getItem(position))
        }
    }

}

class CustomOnArtistListener(val clicklistener : (artists : Artists) -> Unit){
    fun onClick(artists: Artists) = clicklistener(artists)
}

class Diffcallback : DiffUtil.ItemCallback<Artists>(){
    override fun areItemsTheSame(oldItem: Artists, newItem: Artists): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Artists, newItem: Artists): Boolean {
        return oldItem == newItem
    }

}