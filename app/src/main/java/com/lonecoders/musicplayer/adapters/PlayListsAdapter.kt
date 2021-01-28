package com.lonecoders.musicplayer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.databinding.ModalPlaylistsBinding
import com.lonecoders.musicplayer.models.Playlists

class PlayListsAdapter(val listener: PlayListsClickListener) : ListAdapter<Playlists,PlaylistsViewHolder>(MyDiffUtilCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolder {
        val binding = DataBindingUtil.inflate<ModalPlaylistsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.modal_playlists,
            parent,
            false
        )

        return PlaylistsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.onClick(getItem(position))
        }
    }
}

class MyDiffUtilCallBack : DiffUtil.ItemCallback<Playlists>(){
    override fun areItemsTheSame(oldItem: Playlists, newItem: Playlists): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Playlists, newItem: Playlists): Boolean {
        return oldItem == newItem
    }


}

class PlaylistsViewHolder(val binding:ModalPlaylistsBinding):RecyclerView.ViewHolder(binding.root){
    @SuppressLint("SetTextI18n")
    fun bind(playlists: Playlists){
        if(playlists.nof == null){
            binding.playlistArt.setImageResource(R.drawable.ic_baseline_create_new_folder_24)

        }
        else{
            binding.playlistNof.text = "("+playlists.nof+" songs)"
        }
        binding.playlistsName.text = playlists.name

    }

}

class PlayListsClickListener(val listener : (playlists:Playlists)->Unit){
    fun onClick(playlists: Playlists) = listener(playlists)
}
