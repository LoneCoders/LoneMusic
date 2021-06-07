package com.lonecoders.musicplayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lonecoders.musicplayer.models.Song
import com.lonecoders.musicplayer.util.MusicUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SongViewModel(val app: Application) : AndroidViewModel(app) {
    private val job = Job()
    val showRefresh = MutableLiveData<Boolean>()
    var songSet = MutableLiveData<MutableList<Song>>()
    var refresh: Boolean = true

    init {
        refresh()

    }

    fun refresh() {
        showRefresh.value = true
        CoroutineScope(job + Dispatchers.Main).launch {
            songSet.value =
                Song.getSongsFromCursor(MusicUtils.getCursorForSongs(app.baseContext))
            if (songSet.value != null)
                songSet.value!!.sortBy {
                    it.songName
                }
            showRefresh.value = false
        }

    }
}


class SongVmFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            return SongViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
