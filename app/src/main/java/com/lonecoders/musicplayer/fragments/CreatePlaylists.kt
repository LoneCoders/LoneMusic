package com.lonecoders.musicplayer.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.database.PlayListsDb
import com.lonecoders.musicplayer.databinding.FragmentCreatePlaylistsBinding
import com.lonecoders.musicplayer.models.Playlists
import com.lonecoders.musicplayer.models.Song

class CreatePlaylists : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCreatePlaylistsBinding>(
            layoutInflater,R.layout.fragment_create_playlists,container,false
        )
        val viewModel =  ViewModelProviders.of(
            this,PlayListsVMF(requireNotNull(activity).application, PlayListsDb.getInstance(requireContext()))
        ).get(PlayListsViewModel::class.java)
        val songLists = mutableListOf<Song>()
        val args : CreatePlaylistsArgs by navArgs()
        var dialog : AlertDialog? = null

            var selectedItems = emptyArray<CharSequence>()
            for(item in args.songs){
                selectedItems = selectedItems.plusElement(item.songName)
            }
            dialog = AlertDialog.Builder(requireContext())
                .apply {


                    setTitle("Choose songs")
                    setPositiveButton("DONE", DialogInterface.OnClickListener { dialogInterface, i ->

                    })
                    setMultiChoiceItems(selectedItems,null,
                        DialogInterface.OnMultiChoiceClickListener { dialogInterface, i, b ->
                            if(b) {
                                songLists.add(viewModel.songSet.value!![i])
                                Log.i("TESTT", viewModel.songSet.value!![i].songName)
                            }
                            else
                                songLists.remove(viewModel.songSet.value!![i])
                        })
                }
                .create()



        binding.songsPicker.setOnClickListener {
            dialog!!.show()

        }



        binding.songsPickerDone.setOnClickListener {
            val list = Playlists(name = binding.playListsTitle.text.toString(),songs = songLists,nof=songLists.size)
            for( item in songLists){
                Log.i("TEST",item.songName);
            }
            viewModel.insertPlayLists(list)
            requireView().findNavController().navigateUp()
        }

        return binding.root
    }

}