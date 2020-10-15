package com.lonecoders.musicplayer.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.databinding.ActivityMainBinding
import com.lonecoders.musicplayer.databinding.ActivityPermissionErrorBinding
import com.lonecoders.musicplayer.databinding.SplashScreenBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<SplashScreenBinding>(this,R.layout.splash_screen)
        // Runtime permission for Android 6.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                and (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ) {
                binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            }
            else{
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 1
                )

            }
        }
        else
            binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            else {
                //temporary need to change it
                DataBindingUtil.setContentView<ActivityPermissionErrorBinding>(this,R.layout.activity_permission_error)
            }
        }
    }
}