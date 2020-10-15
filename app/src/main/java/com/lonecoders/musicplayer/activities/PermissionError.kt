package com.lonecoders.musicplayer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonecoders.musicplayer.R

class PermissionError : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_error)
    }
}