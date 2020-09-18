package com.lonecoders.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.AlbumAdapter
import com.lonecoders.musicplayer.adapters.CustomOnClickListener
import com.lonecoders.musicplayer.models.Album
import com.lonecoders.musicplayer.util.MusicUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val job = Job()
        var albumSet: Set<Album>

        CoroutineScope(job + Dispatchers.Main).launch {
            albumSet = MusicUtils.albumListToAlbumSet(
                Album.getAlbums(
                    MusicUtils.getCursorForAlbums(requireContext()), requireContext()
                )
            )
            view.findViewById<RecyclerView>(R.id.album_list).apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = AlbumAdapter(albumSet, CustomOnClickListener {
                    //back stack not implemented
                    view.findViewById<FrameLayout>(R.id.album_frame).removeAllViews()
                    val fragment = AlbumInFragment(it.albumSongs)
                    childFragmentManager.beginTransaction().replace(R.id.album_frame, fragment)
                        .commit()
                })
            }
        }

    }

}