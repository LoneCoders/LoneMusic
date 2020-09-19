package com.lonecoders.musicplayer.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
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

        val viewModelF = AlbumVMFactory(requireNotNull(activity).application)
        val viewModel = ViewModelProviders.of(this,viewModelF).get(
            AlbumViewModel::class.java
        )
        lateinit var adapter : AlbumAdapter
        view.findViewById<RecyclerView>(R.id.album_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = AlbumAdapter(CustomOnClickListener {
                //back stack not implemented
                view.findViewById<FrameLayout>(R.id.album_frame).removeAllViews()
                val fragment = AlbumInFragment(it.albumSongs)
                childFragmentManager.beginTransaction().replace(R.id.album_frame, fragment)
                    .commit()

            })
            this.adapter = adapter
        }

        viewModel.albumSet.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it.toSet().toList())

        })


    }

}

class AlbumViewModel(val app : Application) : AndroidViewModel(app){
    private val job = Job()
    var albumSet = MutableLiveData<MutableList<Album>>()
    init {
        refresh()
    }
    fun refresh(){
        CoroutineScope(job + Dispatchers.Main).launch {
            albumSet.value =
                Album.getAlbums(
                    MusicUtils.getCursorForAlbums(app.baseContext),app.baseContext)

        }
    }
}


class AlbumVMFactory(val app : Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
