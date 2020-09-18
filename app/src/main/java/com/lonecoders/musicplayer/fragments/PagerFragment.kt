package com.lonecoders.musicplayer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.activities.AboutActivity
import com.lonecoders.musicplayer.adapters.ViewPagerAdapter
import com.lonecoders.musicplayer.databinding.PagerBinding


class PagerFragment : Fragment() {
    lateinit var binding : PagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.pager,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter = ViewPagerAdapter(requireFragmentManager())
        binding.tabLayout.setupWithViewPager(
            binding.pager
        )
        binding.toolbar
            .setOnMenuItemClickListener {
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
    }
}