package com.lonecoders.musicplayer.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityAboutBinding>(this, R.layout.activity_about)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

    }
}

class AboutPreference : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {


        setPreferencesFromResource(R.xml.about_preference, "my_key")
        //Visit us
        findPreference<Preference>("github")?.setOnPreferenceClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://github.com/LoneCoders")
                startActivity(this)
            }
            true
        }
        //Send feedback
        findPreference<Preference>("feedback")?.setOnPreferenceClickListener {
            Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "gokulrajan01234@gmail.com", null))
                .apply { startActivity(this) }
            true
        }
        //version number
        findPreference<Preference>("version")?.setOnPreferenceClickListener {
            Toast.makeText(context, "eng build", Toast.LENGTH_SHORT).show()
            true
        }
        //check version
        //Yet to implement
        findPreference<Preference>("check_new_version")?.setOnPreferenceClickListener {
            Toast.makeText(context, "Already at new version", Toast.LENGTH_SHORT).show()
            true
        }

    }
}
