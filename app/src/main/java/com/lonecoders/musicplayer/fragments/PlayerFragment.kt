package com.lonecoders.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = DataBindingUtil.inflate<FragmentPlayerBinding>(inflater, R.layout.fragment_player,container,false)
        val args : PlayerFragmentArgs by navArgs()
        val player = SimpleExoPlayer.Builder(requireContext()).build()
        val playerView = binding.playerview
        playerView.player = player
        player.setMediaItem(MediaItem.fromUri(args.song.songUri))
        player.prepare()
        player.play()
        return binding.root
    }
}