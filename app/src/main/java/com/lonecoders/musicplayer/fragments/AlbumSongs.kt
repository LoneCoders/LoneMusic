package com.lonecoders.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.SongsAdapter
import com.lonecoders.musicplayer.models.Album

class AlbumSongs : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.songs_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : AlbumSongsArgs by navArgs()
        val album = args.album
        var albumSongs = Album.getSongsByAlbumId(requireContext(), album.albumName)
        Glide.with(this)
            .load(albumSongs[0].songAlbumCoverUri)
            .placeholder(R.drawable.ic_album_cover_default)
            .centerCrop()
            .into(view.findViewById(R.id.album_in_cover_header))

        val albumNameTextView = view.findViewById<TextView>(R.id.album_in_name)
        albumNameTextView.text = album.albumName
        albumNameTextView.isSelected = true


        view.findViewById<RecyclerView>(R.id.album_in_songs_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SongsAdapter(SongsAdapter.SongsClickListener {
                val pos = albumSongs.indexOf(it)
                requireView().findNavController().navigate(AlbumSongsDirections.actionAlbumSongsFragmentToPlayerFragment(pos,
                    albumSongs.toTypedArray()
                ))
            }).apply {  submitList(albumSongs)}

        }
    }
}