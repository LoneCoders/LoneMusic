package com.lonecoders.musicplayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lonecoders.musicplayer.models.Album
import com.lonecoders.musicplayer.util.FormatList
import com.lonecoders.musicplayer.util.MusicUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlbumViewModel(val app: Application) : AndroidViewModel(app) {
    private val job = Job()
    val showRefresh = MutableLiveData<Boolean>()
    var albumSet = MutableLiveData<MutableList<Album>>()
    var refresh: Boolean = true

    init {
        refresh()

    }

    fun refresh() {
        showRefresh.value = true
        CoroutineScope(job + Dispatchers.Main).launch {


            albumSet.value = FormatList().formatAlbum(
                Album.getAlbums(
                    MusicUtils.getCursorForAlbums(app.baseContext)
                )
            )

            showRefresh.value = false
        }

    }
}


class AlbumVMFactory(val app: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}