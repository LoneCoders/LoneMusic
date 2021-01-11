package com.lonecoders.musicplayer.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.databinding.ActivityMainBinding
import com.lonecoders.musicplayer.databinding.SplashScreenBinding


class SplashScreen : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        DataBindingUtil.setContentView<SplashScreenBinding>(this, R.layout.splash_screen)



        Handler().postDelayed(Runnable {
            //Intent is used to switch from one activity to another.
            startActivity(Intent(
                this@SplashScreen,
                MainActivity::class.java
            ))
            //invoke the SecondActivity.
            finish()
            //the current activity will get finished.
        }, 800)
    }


}