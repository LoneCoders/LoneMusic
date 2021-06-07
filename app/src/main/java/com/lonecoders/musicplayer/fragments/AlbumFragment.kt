package com.lonecoders.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.AlbumAdapter
import com.lonecoders.musicplayer.adapters.AlbumClickListener
import com.lonecoders.musicplayer.databinding.FragmentAlbumBinding
import com.lonecoders.musicplayer.viewmodels.AlbumVMFactory
import com.lonecoders.musicplayer.viewmodels.AlbumViewModel

class AlbumFragment : Fragment() {

    /**
     * Data Binding instance
     */
    private lateinit var binding: FragmentAlbumBinding

    /**
     * ViewModel
     */
    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * Inflate the layout for this fragment
         */
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * ViewModel init
         */
        val viewModelF = AlbumVMFactory(requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(this, viewModelF).get(
            AlbumViewModel::class.java
        )

        /**
         * RecyclerView and its adapter
         */
        lateinit var adapter: AlbumAdapter
        binding.albumList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            /**
             * Adapter with SongClickListener implementation
             * On click, It navigates to the AlbumSongs fragment with clicked album
             */
            adapter = AlbumAdapter(AlbumClickListener {
                requireView().findNavController()
                    .navigate(PagerFragmentDirections.actionPagerFragmentToAlbumSongFragment(it))

            })
            this.adapter = adapter
        }

        /**
         * Observe the album list LiveData and Update the list in the adapter
         */
        viewModel.albumSet.observe(viewLifecycleOwner, {
            adapter.submitList(it.toSet().toList())
        })

        /**
         * Refresh action
         */
        binding.refresh.setOnRefreshListener {
            viewModel.refresh()
        }

        /**
         * Refresh action visibility
         */
        viewModel.showRefresh.observe(viewLifecycleOwner, {
            binding.refresh.isRefreshing = it
        })

    }

}
