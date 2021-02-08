package com.lonecoders.musicplayer.fragments

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.services.MyMediaService
import com.lonecoders.musicplayer.databinding.FragmentPlayerBinding
import com.lonecoders.musicplayer.models.Song

class PlayerFragment : Fragment() {


    private lateinit var mediaBrowser: MediaBrowserCompat
    private lateinit var binding:FragmentPlayerBinding
    private var position : Int? = null
    private var songLists: Array<Song>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player,container,false)
        val args : PlayerFragmentArgs by navArgs()
        position = args.position
        songLists = args.songLists

        return binding.root
    }

    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback(){
        override fun onConnected() {
            super.onConnected()
            mediaBrowser.sessionToken.also { token ->

                // Create a MediaControllerCompat
                val mediaController = MediaControllerCompat(
                    requireContext(), // Context
                    token
                )

                // Save the controller
                MediaControllerCompat.setMediaController(requireActivity(), mediaController)
            }

            // Finish building the UI
            buildTransportControls()

        }
    }
    private val controllerCallback = object : MediaControllerCompat.Callback(){
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            binding.playrpause.setImageResource(
                when(state!!.state){
                    PlaybackStateCompat.STATE_PLAYING -> R.drawable.ic_baseline_pause_circle_filled_24
                    else -> R.drawable.ic_baseline_play_circle_filled_24
                }
            )
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
        }
    }
    private  fun buildTransportControls(){
        val mainButton = binding.playrpause
        val mediaController = MediaControllerCompat.getMediaController(requireActivity())
        mediaController.transportControls.prepare()
        val bundle = Bundle().apply {
            putInt("position", position!!)
            putParcelableArray("songLists",songLists!!)
        }
        mediaController.transportControls.playFromUri(
            Uri.parse( songLists!![position!!].songUri),bundle)
        mainButton.setOnClickListener {
            val state = mediaController.playbackState.state
            if(state == PlaybackStateCompat.STATE_PLAYING)
                mediaController.transportControls.pause()
            else
                mediaController.transportControls.play()
        }

        binding.playNext.setOnClickListener {
            mediaController.transportControls.skipToNext()
        }
        binding.playPrev.setOnClickListener {
            mediaController.transportControls.skipToPrevious()
        }

        mediaController.registerCallback(controllerCallback)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaBrowser = MediaBrowserCompat(
            requireContext(),
            ComponentName(requireContext(), MyMediaService::class.java),
            connectionCallbacks,
            null // optional Bundle
        )


    }

    override fun onStart() {
        super.onStart()
        mediaBrowser.connect()
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        mediaBrowser.disconnect()
    }
}