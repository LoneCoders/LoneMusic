package com.lonecoders.musicplayer.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore.Audio.Media.*
import com.lonecoders.musicplayer.models.Album

class MusicUtils {

    companion object {
       fun getCursorForSongs(context: Context): Cursor? {
            val contentResolver = context.contentResolver
            val musicUri: Uri = EXTERNAL_CONTENT_URI
            val projection = arrayOf(_ID, TITLE, ALBUM, ALBUM_ID, ARTIST, ARTIST_ID)
            return contentResolver.query(musicUri, projection, null, null, null)
        }

        fun getCursorForAlbums(context: Context): Cursor? {
            val contentResolver = context.contentResolver
            val musicUri: Uri = EXTERNAL_CONTENT_URI
            val projection =
                arrayOf(
                    ALBUM_ID,
                    ALBUM,
                    ARTIST
                )
            return contentResolver.query(musicUri, projection, null, null, null)
        }

        fun albumListToAlbumSet(list: MutableList<Album>):Set<Album>{
            return list.toSet()
        }
    }
}