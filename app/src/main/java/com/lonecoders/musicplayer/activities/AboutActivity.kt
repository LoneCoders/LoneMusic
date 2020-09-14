package com.lonecoders.musicplayer.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.databinding.ActivityAboutBinding
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityAboutBinding>(this, R.layout.activity_about)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.githublink.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {

                setData(Uri.parse("https://github.com/LoneCoders"))
                startActivity(this)
            }
        }
        for( view in listOf<TextView>(devMail,dhinaMail,samMail))
            view.setOnClickListener {
                when(it){
                    devMail ->Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto", "gokulrajan01234@gmail.com", null)).apply { startActivity(this) }
                    samMail ->Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto", "gokulrajan01234@gmail.com", null)).apply { startActivity(this) }
                    dhinaMail->Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto", "dhinalogu@gmail.com", null)).apply { startActivity(this) }
                }
            }

    }
}