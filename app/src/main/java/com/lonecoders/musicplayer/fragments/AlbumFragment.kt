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
import androidx.recyclerview.widget.GridLayoutManager
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.AlbumAdapter
import com.lonecoders.musicplayer.adapters.CustomOnClickListener
import com.lonecoders.musicplayer.databinding.FragmentAlbumBinding
import com.lonecoders.musicplayer.models.Album
import com.lonecoders.musicplayer.util.FormatList
import com.lonecoders.musicplayer.util.MusicUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding
    lateinit var viewModel : AlbumViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_album,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelF = AlbumVMFactory(requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(this,viewModelF).get(
            AlbumViewModel::class.java
        )
        lateinit var adapter : AlbumAdapter
        binding.albumList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = AlbumAdapter(CustomOnClickListener {
                requireView().findNavController().navigate(PagerFragmentDirections.actionPagerFragmentToAlbumSongFragment(it))

            })
            this.adapter = adapter
        }

        viewModel.albumSet.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it.toSet().toList())

        })
        //Refresh action
        binding.refresh.setOnRefreshListener {
            viewModel.refresh()
        }
        //Refresh action visibility
        viewModel.showRefresh.observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = it
        })



    }


}

class AlbumViewModel(val app : Application) : AndroidViewModel(app){
    private val job = Job()
    val showRefresh = MutableLiveData<Boolean>()
    var albumSet = MutableLiveData<MutableList<Album>>()
    var refresh : Boolean = true
    init {
        refresh()

    }
    fun refresh(){
        showRefresh.value = true
        CoroutineScope(job + Dispatchers.Main).launch {


            albumSet.value = FormatList().formatAlbum(
                Album.getAlbums(
                    MusicUtils.getCursorForAlbums(app.baseContext))
            )

            showRefresh.value = false
        }

    }
}


class AlbumVMFactory(val app : Application) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
