package com.lonecoders.musicplayer.models

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore.Audio.AlbumColumns.ALBUM
import android.provider.MediaStore.Audio.AlbumColumns.ALBUM_ID
import com.lonecoders.musicplayer.util.MusicUtils

data class Album(
    val albumName: String,
    val albumId: Long,
    val albumSongs: MutableList<Song>
) {
    companion object {
        fun getAlbums(cursor: Cursor?, c: Context): MutableList<Album> {
            val albumList = mutableListOf<Album>()
            cursor?.use {
                val albumIdColumn = it.getColumnIndex(ALBUM_ID)
                val albumNameColumn = it.getColumnIndex(ALBUM)

                if (it.moveToFirst()) {
                    do {
                        val thisAlbumId = it.getLong(albumIdColumn)
                        val thisAlbumName = it.getString(albumNameColumn)
                        val thisAlbumSongs =
                            Song.getSongsFromCursor(MusicUtils.getCursorForSongs(c), thisAlbumId)
                        albumList += Album(
                            thisAlbumName,
                            thisAlbumId,
                            thisAlbumSongs
                        )

                    } while (it.moveToNext())
                    it.close()
                }
            }
            return albumList
        }
    }
}
