package com.lonecoders.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.SongsAdapter
import com.lonecoders.musicplayer.adapters.SongsClickListener
import com.lonecoders.musicplayer.models.Song
import com.lonecoders.musicplayer.util.MusicUtils

class ArtistSongs : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.songs_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : ArtistSongsArgs by navArgs()
        val artist = args.arists
        var artistSongs = Song.getSongsFromCursor(artist.artistName,MusicUtils.getCursorForSongs(requireContext()))


        view.findViewById<RecyclerView>(R.id.album_in_songs_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SongsAdapter(SongsClickListener {  }).apply {  submitList(artistSongs)}

        }
    }
}