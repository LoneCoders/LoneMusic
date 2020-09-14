package com.lonecoders.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.lonecoders.musicplayer.databinding.ActivityMainBinding
import com.lonecoders.musicplayer.util.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.pager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.pager)
        binding.toolbar.apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.about -> {
                        Intent(applicationContext, AboutActivity::class.java).apply {
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


}