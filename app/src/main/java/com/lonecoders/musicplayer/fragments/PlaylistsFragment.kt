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
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.PlayListsAdapter
import com.lonecoders.musicplayer.adapters.PlayListsClickListener
import com.lonecoders.musicplayer.database.PlayListsDb
import com.lonecoders.musicplayer.databinding.FragmentPlaylistsBinding
import com.lonecoders.musicplayer.models.Playlists
import com.lonecoders.musicplayer.models.Song
import com.lonecoders.musicplayer.util.MusicUtils
import kotlinx.coroutines.*


class PlaylistsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPlaylistsBinding>(
            inflater, R.layout.fragment_playlists,container,false
        )

        val viewModel =  ViewModelProviders.of(
            this,PlayListsVMF(requireNotNull(activity).application, PlayListsDb.getInstance(requireContext()))
        ).get(PlayListsViewModel::class.java)
        binding.setLifecycleOwner { this.lifecycle }

        val adapter = PlayListsAdapter(PlayListsClickListener {
            if(it.nof == null && viewModel.songSet.value != null){
                requireView().findNavController()
                        .navigate(PagerFragmentDirections.actionPagerFragmentToCreatePlaylists(
                                viewModel.songSet.value!!.toTypedArray()
                        ))
            }
            else{
                //Navigate to Playlists Songs
                requireView().findNavController()
                        .navigate(PagerFragmentDirections.actionPagerFragmentToPlayListSongs(it.id))
            }
        })

        viewModel.playLists.observe(viewLifecycleOwner, Observer {
           mutableListOf(Playlists(name = "Create PlayLists")).let {list->
                list.addAll(viewModel.playLists.value!!)
                adapter.submitList(list)
            }
        })

        binding.playlists.adapter = adapter
        return binding.root
    }


}

class PlayListsViewModel(app : Application, private val database:PlayListsDb) : AndroidViewModel(app){
    val playLists = database.playListsDao.getAll()
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)
    var songSet = MutableLiveData<MutableList<Song>>()

    init{
        CoroutineScope(job + Dispatchers.Main).launch {
            songSet.value =
                Song.getSongsFromCursor(MusicUtils.getCursorForSongs(app.baseContext))
            if (songSet.value != null)
                songSet.value!!.sortBy {
                    it.songName
                }
        }
    }

    fun insertPlayLists(playlists: Playlists){
        coroutineScope.launch {
            insert(playlists)
        }
    }
    private suspend fun insert(playlists: Playlists){
         withContext(Dispatchers.IO){
            database.playListsDao.insert(playlists)
        }
    }
}

class PlayListsVMF(val app : Application, private val database: PlayListsDb): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayListsViewModel::class.java)){
            return PlayListsViewModel(app,database) as T
        }
        throw IllegalAccessException("Unknown viewModel")
    }
}
