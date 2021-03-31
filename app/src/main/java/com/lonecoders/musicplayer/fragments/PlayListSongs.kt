package com.lonecoders.musicplayer.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.SongsAdapter
import com.lonecoders.musicplayer.database.PlayListsDb
import com.lonecoders.musicplayer.databinding.SongsListBinding

class PlayListSongs : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // require playlists
        //connect with view model
        // ids = [album_in_name,album_in_songs_list]
        //need id from prev fragment
        val binding = DataBindingUtil.inflate<SongsListBinding> (inflater,R.layout.songs_list, container, false)
        val args : PlayListSongsArgs by navArgs()
        val viewModel = ViewModelProviders.of(this,
            PlayListSongVMF(requireNotNull(activity).application, PlayListsDb.getInstance(requireContext()),args.playListID))
            .get(PlayListSongVM::class.java)
        val adapter = SongsAdapter(SongsAdapter.SongsClickListener {
            if(viewModel.playlist.value != null) {
                val pos = viewModel.playlist.value!!.songs!!.indexOf(it)
                requireView().findNavController().navigate(
                    PlayListSongsDirections
                        .actionPlayListSongsToPlayerFragment(
                            pos,
                            viewModel.playlist.value!!.songs!!.toTypedArray()
                        )
                )
            }
        })



       binding.albumInSongsList.layoutManager = LinearLayoutManager(requireContext())
     //   viewModel.getPlayLists(args.playListID)
        viewModel.playlist.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Glide.with(this)
                    .load(viewModel.playlist.value!!.songs!![0].songAlbumCoverUri)
                    .placeholder(R.drawable.ic_album_cover_default)
                    .centerCrop()
                    .into(requireView().findViewById(R.id.album_in_cover_header))
                Log.i("fff",args.playListID.toString())
                binding.albumInName.text = it.name
                adapter.submitList(it.songs)
                binding.albumInSongsList.adapter = adapter
            }


        })
        binding.albumInSongsList.adapter = adapter
        return binding.root
    }


}

class PlayListSongVM(app : Application, db : PlayListsDb, id: Long) : AndroidViewModel(app){
    var playlist = db.playListsDao.getByID(id)
//    private val job = Job()
//    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)
//    fun getPlayLists(id : Long){
//        coroutineScope.launch {
//            playlist = get(id)
//
//        }
//
//    }
//    private suspend fun get(id : Long):LiveData<Playlists>{
//        return withContext(Dispatchers.IO){
//            db.playListsDao.getByID(id)
//        }
//    }

}

class PlayListSongVMF(val app : Application, private val db : PlayListsDb, val id : Long) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayListSongVM::class.java))
            return PlayListSongVM(app,db,id) as T
        throw (IllegalAccessException("unknown viewmodel"))
    }

}
