package com.lonecoders.musicplayer.models

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore.Audio.AlbumColumns.ALBUM
import android.provider.MediaStore.Audio.AlbumColumns.ALBUM_ID
import com.lonecoders.musicplayer.util.MusicUtils
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Parcelize
data class Album(
    val albumName: String,
    val albumId: Long,
    val albumCoverUri: Uri
):Parcelable {

    companion object {
        suspend fun getAlbums(cursor: Cursor?, c: Context): MutableList<Album> {
            return withContext(Dispatchers.IO) {
                val albumList = mutableListOf<Album>()
                cursor?.use {
                    it.moveToFirst()
                        val albumIdColumn = it.getColumnIndex(ALBUM_ID)
                        val albumNameColumn = it.getColumnIndex(ALBUM)
                        do {
                            val thisAlbumId = it.getLong(albumIdColumn)
                            val thisAlbumName = it.getString(albumNameColumn)


                            val artUri = Uri.parse("content://media/external/audio/albumart")
                            val thisAlbumCoverUri = ContentUris.withAppendedId(artUri,thisAlbumId)
                            albumList += Album(
                                thisAlbumName,
                                thisAlbumId,
                                thisAlbumCoverUri
                            )
                        } while (it.moveToNext())
                }
                albumList
            }
        }
        fun getSongsByAlbumId(c:Context,album: String):MutableList<Song>{
            return Song.getSongsFromCursor(
                    MusicUtils.getCursorForSongs(c),
                    album
                )
        }
    }
}

