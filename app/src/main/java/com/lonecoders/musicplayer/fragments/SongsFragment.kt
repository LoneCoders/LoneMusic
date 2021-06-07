package com.lonecoders.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.SongsAdapter
import com.lonecoders.musicplayer.adapters.SongsClickListener
import com.lonecoders.musicplayer.databinding.FragmentSongsBinding
import com.lonecoders.musicplayer.viewmodels.SongViewModel
import com.lonecoders.musicplayer.viewmodels.SongVmFactory

class SongsFragment : Fragment() {

    /**
     * Data binding instance
     */
    private lateinit var binding: FragmentSongsBinding

    /**
     * ViewModel
     */
    private lateinit var viewModel: SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * Inflate the layout for this fragment
         */
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_songs, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * ViewModel Init
         */
        val viewModelF = SongVmFactory(requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(this, viewModelF).get(SongViewModel::class.java)

        /**
         * Recycler view and its adapter
         */
        lateinit var adapter: SongsAdapter
        binding.songList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            /**
             * Adapter with SongClickListener implementation
             * On click, It navigates to the player fragment with position and song set
             */
            adapter = SongsAdapter(SongsClickListener {
                val pos = viewModel.songSet.value!!.indexOf(it)
                view.findNavController().navigate(
                    PagerFragmentDirections.actionPagerFragmentToPlayerFragment(
                        pos,
                        viewModel.songSet.value!!.toTypedArray()
                    )
                )
            })
            this.adapter = adapter
        }

        /**
         * Observe the song list LiveData and Update the list in the adapter
         */
        viewModel.songSet.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        /**
         * Refresh action
         */
        binding.refreshSong.setOnRefreshListener {
            viewModel.refresh()
        }

        /**
         * Refresh action visibility
         */
        viewModel.showRefresh.observe(viewLifecycleOwner, {
            binding.refreshSong.isRefreshing = it
        })
    }
}
