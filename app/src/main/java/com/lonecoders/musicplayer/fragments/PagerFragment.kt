package com.lonecoders.musicplayer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.activities.AboutActivity
import com.lonecoders.musicplayer.adapters.ViewPager2Adapter
import com.lonecoders.musicplayer.databinding.FragmentPagerBinding


class PagerFragment : Fragment() {

    lateinit var binding : FragmentPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_pager, container, false)

        binding.pager.adapter = ViewPager2Adapter(this)
        TabLayoutMediator(binding.tabLayout,binding.pager){tab: TabLayout.Tab, position: Int ->
            tab.text = when(position){
                0 -> "Songs"
                1 -> "Album"
                2 -> "Artist"
                3 -> "Playlists"
                else -> ""
            }

        }.attach()
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.about -> {
                    Intent(context, AboutActivity::class.java).apply {
                        startActivity(
                            this
                        )
                    }
                    true
                }
                else -> false
            }

        }

        return binding.root
    }


}