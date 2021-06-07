package com.lonecoders.musicplayer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.activities.AboutActivity
import com.lonecoders.musicplayer.adapters.ViewPager2Adapter
import com.lonecoders.musicplayer.databinding.FragmentPagerBinding


class PagerFragment : Fragment() {

    /**
     * Data Binding instance
     */
    private lateinit var binding: FragmentPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * Inflate the layout for this fragment
         */
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_pager,
            container,
            false
        )

        /**
         * Set the adapter for ViewPager
         */
        binding.pager.adapter = ViewPager2Adapter(this)

        /**
         * Bottom Navigation View
         * Change the current page of the viewpager with selected item
         */
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            val currentPage = when (it.itemId) {
                R.id.songs_dest -> 0
                R.id.albums_dest -> 1
                R.id.playlist_dest -> 2
                else -> 0
            }
            binding.pager.currentItem = currentPage

            return@setOnNavigationItemSelectedListener true
        }

        /**
         * Update the bottom navigation view item checked status after swiping in viewpager
         */
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        /**
         * Toolbar menu items handling
         */
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