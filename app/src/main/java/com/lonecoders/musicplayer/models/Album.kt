package com.lonecoders.musicplayer.models

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.provider.MediaStore.Audio.AlbumColumns.ALBUM
import android.provider.MediaStore.Audio.AlbumColumns.ALBUM_ID
import com.lonecoders.musicplayer.util.MusicUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Album(
    val albumName: String,
    val albumId: Long,
    val albumSongs: MutableList<Song>,
    val albumCover: Bitmap?
) {

    companion object {
        suspend fun getAlbums(cursor: Cursor?, c: Context): MutableList<Album> {
            return withContext(Dispatchers.IO) {
                val albumList = mutableListOf<Album>()
                cursor?.use {
                    if (it.moveToFirst()) {
                        val albumIdColumn = it.getColumnIndex(ALBUM_ID)
                        val albumNameColumn = it.getColumnIndex(ALBUM)
                        do {
                            val thisAlbumId = it.getLong(albumIdColumn)
                            val thisAlbumName = it.getString(albumNameColumn)
                            val thisAlbumSongs =
                                Song.getSongsFromCursor(
                                    c,
                                    MusicUtils.getCursorForSongs(c),
                                    thisAlbumId
                                )

                            // Get Album cover from Song
                            val thisAlbumCover = thisAlbumSongs[0].songAlbumCover // Any song in this album.
                            albumList += Album(
                                thisAlbumName,
                                thisAlbumId,
                                thisAlbumSongs,
                                thisAlbumCover
                            )

                        } while (it.moveToNext())
                        it.close()
                    }
                }
                albumList
            }
        }
    }
}

