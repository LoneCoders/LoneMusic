package com.lonecoders.musicplayer.Service

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.lonecoders.musicplayer.models.Song

private const val MY_MEDIA_ROOT_ID = "lone_media_root_id"
private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"


class MyMediaService : MediaBrowserServiceCompat(){

    private var mediaSession : MediaSessionCompat? = null
    private lateinit var stateBuilder : PlaybackStateCompat.Builder
    private var player : SimpleExoPlayer? = null
    private val mediaSessionCallBack = object : MediaSessionCompat.Callback() {

        override fun onSeekTo(pos: Long) {
            super.onSeekTo(pos)
        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            player!!.previous()
        }

        override fun onPrepare() {
            super.onPrepare()
            startService(Intent(applicationContext,MyMediaService::class.java))

        }

        override fun onPlay() {
            super.onPlay()
            player!!.play()
            stateBuilder = PlaybackStateCompat.Builder().setState(
                PlaybackStateCompat.STATE_PLAYING,
                player!!.currentPosition.toLong(),
                1000.toFloat()
            )
            mediaSession!!.setPlaybackState(stateBuilder.build())
        }

        override fun onStop() {
            super.onStop()
            player!!.stop()
            player!!.release()
            player = null
        }

        override fun onSkipToNext() {
            super.onSkipToNext()
            player!!.next()
        }

        override fun onPause() {
            super.onPause()
            player!!.pause()
            stateBuilder = PlaybackStateCompat.Builder().setState(
                PlaybackStateCompat.STATE_PAUSED,
                player!!.currentPosition.toLong(),
                1000.toFloat()
            )
            mediaSession!!.setPlaybackState(stateBuilder.build())
        }

        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            super.onPlayFromUri(uri, extras)
            val position = extras!!.getInt("position")
            val songLists : List<Song> = extras.getParcelableArray("songLists")!!.map{
                it as Song
            }
            player!!.release()
            player = SimpleExoPlayer.Builder(applicationContext).build()

            player!!.setMediaItem(MediaItem.fromUri(songLists[position].songUri))
            for(i in 1 .. songLists.size){
                player!!.addMediaItem(MediaItem.fromUri(songLists[(i+position)%songLists.size].songUri))
            }
            player!!.repeatMode = Player.REPEAT_MODE_ALL
            Log.d("FFFF","playing"+uri.toString())
            mediaSession!!.isActive = true
            player!!.apply {
                prepare()
                play()
            }
            stateBuilder = PlaybackStateCompat.Builder().setState(
                PlaybackStateCompat.STATE_PLAYING,
                0,
                1000.toFloat()
            )
            mediaSession!!.setPlaybackState(stateBuilder.build())
        }


    }

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(baseContext, "LOG_TAG").apply {

            // Enable callbacks from MediaButtons and TransportControls
            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                    or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )

            // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
            stateBuilder = PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY
                        or PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            setPlaybackState(stateBuilder.build())

            // MySessionCallback() has methods that handle callbacks from a media controller
            setCallback(mediaSessionCallBack)

            // Set the session's token so that client activities can communicate with it.
            setSessionToken(sessionToken)
        }
        player = SimpleExoPlayer.Builder(applicationContext).build()

    }



    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        result.sendResult(mutableListOf<MediaBrowserCompat.MediaItem>() )
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(MY_MEDIA_ROOT_ID, null)

    }

}