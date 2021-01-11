package com.lonecoders.musicplayer.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.SongsAdapter
import com.lonecoders.musicplayer.databinding.FragmentSongsBinding
import com.lonecoders.musicplayer.models.Song
import com.lonecoders.musicplayer.util.MusicUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.nio.channels.Selector

class SongsFragment : Fragment() {
    lateinit var binding : FragmentSongsBinding
    lateinit var viewModel : SongViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_songs,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelF = SongVmFactory(requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(this,viewModelF).get(SongViewModel::class.java)
        lateinit var adapter : SongsAdapter
        binding.songList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = SongsAdapter(SongsAdapter.SongsClickListener {
                val pos = viewModel.songSet.value!!.indexOf(it)
                view.findNavController().navigate(PagerFragmentDirections.
                actionPagerFragmentToPlayerFragment(pos, viewModel.songSet.value!!.toTypedArray()))
            })
            this.adapter = adapter
        }

        viewModel.songSet.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it)

        })
        //Refresh action
        binding.refreshSong.setOnRefreshListener {
            viewModel.refresh()
        }
        //Refresh action visibility
        viewModel.showRefresh.observe(viewLifecycleOwner, Observer {
            view.findViewById<SwipeRefreshLayout>(R.id.refresh_song).isRefreshing = it
        })



    }

}

class SongViewModel(val app : Application) : AndroidViewModel(app){
    private val job = Job()
    val showRefresh = MutableLiveData<Boolean>()
    var songSet = MutableLiveData<MutableList<Song>>()
    var refresh : Boolean = true
    init {
        refresh()

    }
    fun refresh(){
        showRefresh.value = true
        CoroutineScope(job + Dispatchers.Main).launch {
            songSet.value =
                Song.getSongsFromCursor(MusicUtils.getCursorForSongs(app.baseContext))
            showRefresh.value = false
        }

    }
}


class SongVmFactory(val app : Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            return SongViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
