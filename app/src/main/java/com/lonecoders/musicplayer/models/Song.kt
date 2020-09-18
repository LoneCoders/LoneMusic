package com.lonecoders.musicplayer.models

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media.*
import android.util.Size
import java.io.FileNotFoundException

data class Song(
    val songId: Long,
    val songName: String,
    val albumName: String,
    val albumId: Long,
    val artistName: String,
    val songAlbumCover: Bitmap?
) {
    companion object {
        fun getSongsFromCursor(context: Context, cursor: Cursor?): MutableList<Song> {
            val songList = mutableListOf<Song>()
            cursor?.use {
                val idColumn = it.getColumnIndex(_ID)
                val titleColumn = it.getColumnIndex(TITLE)
                val albumColumn = it.getColumnIndex(ALBUM)
                val albumIdColumn = it.getColumnIndex(ALBUM_ID)
                val artistColumn = it.getColumnIndex(ARTIST)

                val coverColumn = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                    it.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART) else -1

                while (it.moveToNext()) {
                    val thisSongId = it.getLong(idColumn)
                    val thisSongTitle = it.getString(titleColumn)
                    val thisSongAlbum = it.getString(albumColumn)
                    val thisSongAlbumId = it.getLong(albumIdColumn)
                    val thisSongArtist = it.getString(artistColumn)
                    val thisSongAlbumCover = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                        if (coverColumn != -1) {
                            BitmapFactory.decodeFile(it.getString(coverColumn))
                        } else {
                            null
                        } else {
                        try {
                            val songUri =
                                ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, thisSongId)
                            context.contentResolver.loadThumbnail(songUri, Size(72, 72), null)
                        } catch (e: FileNotFoundException) {
                            null
                        }
                    }

                    songList += Song(
                        thisSongId,
                        thisSongTitle,
                        thisSongAlbum,
                        thisSongAlbumId,
                        thisSongArtist,
                        thisSongAlbumCover
                    )
                }
                it.close()
            }

            return songList

        }

        fun getSongsFromCursor(
            context: Context,
            cursor: Cursor?,
            albumId: Long
        ): MutableList<Song> {
            val songList = mutableListOf<Song>()
            cursor?.use {
                val idColumn = it.getColumnIndex(_ID)
                val titleColumn = it.getColumnIndex(TITLE)
                val albumColumn = it.getColumnIndex(ALBUM)
                val albumIdColumn = it.getColumnIndex(ALBUM_ID)
                val artistColumn = it.getColumnIndex(ARTIST)
                val coverColumn = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                    it.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART) else -1

                if (it.moveToFirst())
                    do {
                        val thisSongId = it.getLong(idColumn)
                        val thisSongTitle = it.getString(titleColumn)
                        val thisAlbum = it.getString(albumColumn)
                        val thisAlbumId = it.getLong(albumIdColumn)
                        val thisArtist = it.getString(artistColumn)

                        if (thisAlbumId == albumId) {

                            val thisSongAlbumCover =
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                                    if (coverColumn != -1) {
                                        BitmapFactory.decodeFile(it.getString(coverColumn))
                                    } else {
                                        null
                                    }

                                } else {
                                    try {
                                        val songUri =
                                            ContentUris.withAppendedId(
                                                EXTERNAL_CONTENT_URI,
                                                thisSongId
                                            )
                                        context.contentResolver.loadThumbnail(
                                            songUri,
                                            Size(720, 1280),
                                            null
                                        )
                                    } catch (e: FileNotFoundException) {
                                        null
                                    }
                                }
                            songList += Song(
                                thisSongId,
                                thisSongTitle,
                                thisAlbum,
                                thisAlbumId,
                                thisArtist,
                                thisSongAlbumCover
                            )
                        }
                    } while (it.moveToNext())
                it.close()
            }
            return songList
        }
    }
}
