package com.lonecoders.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.AlbumAdapter
import com.lonecoders.musicplayer.adapters.CustomOnClickListener
import com.lonecoders.musicplayer.databinding.FragmentAlbumBinding
import com.lonecoders.musicplayer.viewmodels.AlbumVMFactory
import com.lonecoders.musicplayer.viewmodels.AlbumViewModel

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    lateinit var viewModel: AlbumViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelF = AlbumVMFactory(requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(this, viewModelF).get(
            AlbumViewModel::class.java
        )
        lateinit var adapter: AlbumAdapter
        binding.albumList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = AlbumAdapter(CustomOnClickListener {
                requireView().findNavController()
                    .navigate(PagerFragmentDirections.actionPagerFragmentToAlbumSongFragment(it))

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
