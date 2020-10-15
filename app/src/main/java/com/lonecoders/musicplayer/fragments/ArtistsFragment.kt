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
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.ArtistsAdapter
import com.lonecoders.musicplayer.adapters.CustomOnArtistListener
import com.lonecoders.musicplayer.databinding.FragmentArtistsBinding
import com.lonecoders.musicplayer.models.Artists
import com.lonecoders.musicplayer.util.FormatList
import com.lonecoders.musicplayer.util.MusicUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArtistsFragment : Fragment() {
    lateinit var binding : FragmentArtistsBinding
    lateinit var viewModel : ArtistsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_artists,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelF = ArtistsVMFactory(requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(this,viewModelF).get(
            ArtistsViewModel::class.java
        )
        lateinit var adapter : ArtistsAdapter
        binding.artistList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = ArtistsAdapter(CustomOnArtistListener {
                requireView().findNavController().navigate(PagerFragmentDirections.actionPagerFragmentToArtistSongs(it))

            })
            this.adapter = adapter
        }

        viewModel.artistSet.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it.toSet().toList())

        })
        //Refresh action
        binding.refreshArtist.setOnRefreshListener {
            viewModel.refresh()
        }
        //Refresh action visibility
        viewModel.showRefresh.observe(viewLifecycleOwner, Observer {
            binding.refreshArtist.isRefreshing = it
        })



    }


}

class ArtistsViewModel(val app : Application) : AndroidViewModel(app){
    private val job = Job()
    val showRefresh = MutableLiveData<Boolean>()
    var artistSet = MutableLiveData<MutableList<Artists>>()
    var refresh : Boolean = true
    init {
        refresh()

    }
    fun refresh(){
        showRefresh.value = true
        CoroutineScope(job + Dispatchers.Main).launch {
            artistSet.value = FormatList().formatArtists(
                Artists.getArtists(
                    MusicUtils.getCursorForArtists(app.baseContext),app.baseContext
                )
            )
            showRefresh.value = false
        }

    }
}


class ArtistsVMFactory(val app : Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistsViewModel::class.java)) {
            return ArtistsViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
