package com.lonecoders.musicplayer.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.adapters.ViewPagerAdapter
import com.lonecoders.musicplayer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Runtime permission for Android 6.0+
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.pager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.pager)
        binding.toolbar.
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