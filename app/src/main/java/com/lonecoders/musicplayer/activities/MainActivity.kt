package com.lonecoders.musicplayer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lonecoders.musicplayer.activities.AboutActivity
import com.lonecoders.musicplayer.adapters.ViewPagerAdapter
import com.lonecoders.musicplayer.databinding.ActivityMainBinding


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