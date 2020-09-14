package com.lonecoders.musicplayer.models

import android.database.Cursor
import android.provider.MediaStore.Audio.Media.*

data class Song(
    val songId: Long,
    val songName: String,
    val albumName: String,
    val albumId: Long,
    val artistName: String
) {
    companion object {
        fun getSongsFromCursor(cursor: Cursor?): MutableList<Song> {
            val songList = mutableListOf<Song>()
            cursor?.use {
                val idColumn = it.getColumnIndex(_ID)
                val titleColumn = it.getColumnIndex(TITLE)
                val albumColumn = it.getColumnIndex(ALBUM)
                val albumIdColumn = it.getColumnIndex(ALBUM_ID)
                val artistColumn = it.getColumnIndex(ARTIST)

                while (it.moveToNext()) {
                    val thisSongId = it.getLong(idColumn)
                    val thisSongTitle = it.getString(titleColumn)
                    val thisSongAlbum = it.getString(albumColumn)
                    val thisSongAlbumId = it.getLong(albumIdColumn)
                    val thisSongArtist = it.getString(artistColumn)
                    songList += Song(
                        thisSongId,
                        thisSongTitle,
                        thisSongAlbum,
                        thisSongAlbumId,
                        thisSongArtist
                    )
                }
                it.close()
            }

            return songList

        }

        fun getSongsFromCursor(cursor: Cursor?, albumId: Long): MutableList<Song> {
            val songList = mutableListOf<Song>()
            cursor?.use {
                val idColumn = it.getColumnIndex(_ID)
                val titleColumn = it.getColumnIndex(TITLE)
                val albumColumn = it.getColumnIndex(ALBUM)
                val albumIdColumn = it.getColumnIndex(ALBUM_ID)
                val artistColumn = it.getColumnIndex(ARTIST)
                if (it.moveToFirst())
                    do {
                        val thisSongId = it.getLong(idColumn)
                        val thisSongTitle = it.getString(titleColumn)
                        val thisAlbum = it.getString(albumColumn)
                        val thisAlbumId = it.getLong(albumIdColumn)
                        val thisArtist = it.getString(artistColumn)

                        if (thisAlbumId == albumId) {
                            songList += Song(
                                thisSongId,
                                thisSongTitle,
                                thisAlbum,
                                thisAlbumId,
                                thisArtist
                            )
                        }
                    } while (it.moveToNext())
                it.close()
            }
            return songList
        }

    }
}
