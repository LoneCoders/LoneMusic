package com.lonecoders.musicplayer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.AlbumInAdapter
import com.lonecoders.musicplayer.models.Song

class AlbumInFragment(private val albumSongs: MutableList<Song>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.album_in_songs_list).apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(requireContext())
            adapter = AlbumInAdapter(albumSongs.toSet())
        }
    }
}