package com.lonecoders.musicplayer.models

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    val songId: Long,
    val songName: String,
    val albumName: String,
    val albumId: Long,
    val artistName: String,
    val songAlbumCoverUri: Uri?
):Parcelable {
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
                    val albumArtUri = Uri.parse("content://media/external/audio/albumart")
                    val thisSongAlbumCoverUri =
                        ContentUris.withAppendedId(albumArtUri, thisSongAlbumId)

                    songList += Song(
                        thisSongId,
                        thisSongTitle,
                        thisSongAlbum,
                        thisSongAlbumId,
                        thisSongArtist,
                        thisSongAlbumCoverUri
                    )
                }
                it.close()
            }

            return songList

        }

        fun getSongsFromCursor(
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

                if (it.moveToFirst())
                    do {
                        val thisSongId = it.getLong(idColumn)
                        val thisSongTitle = it.getString(titleColumn)
                        val thisAlbum = it.getString(albumColumn)
                        val thisAlbumId = it.getLong(albumIdColumn)
                        val thisArtist = it.getString(artistColumn)
                        val albumArtUri = Uri.parse("content://media/external/audio/albumart")
                        val thisSongAlbumCoverUri =
                            ContentUris.withAppendedId(albumArtUri, thisAlbumId)

                        if (thisAlbumId == albumId) {


                            songList += Song(
                                thisSongId,
                                thisSongTitle,
                                thisAlbum,
                                thisAlbumId,
                                thisArtist,
                                thisSongAlbumCoverUri
                            )
                        }
                    } while (it.moveToNext())
                it.close()
            }
            return songList
        }

        fun getSongsFromCursor(artistsId : Long, cursor : Cursor?) : MutableList<Song>{
            val songList = mutableListOf<Song>()
            cursor?.use {
                val idColumn = it.getColumnIndex(_ID)
                val titleColumn = it.getColumnIndex(TITLE)
                val albumColumn = it.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM)
                val artistIdColumn = it.getColumnIndex(ARTIST_ID)
                val artistColumn = it.getColumnIndex(MediaStore.Audio.ArtistColumns.ARTIST)

                if (it.moveToFirst())
                    do {
                        val thisSongId = it.getLong(idColumn)
                        val thisSongTitle = it.getString(titleColumn)
                        val thisAlbum = it.getString(albumColumn)
                        val thisArtistsId = it.getLong(artistIdColumn)
                        val thisArtist = it.getString(artistColumn)


                        if (thisArtistsId == artistsId) {


                            songList += Song(
                                thisSongId,
                                thisSongTitle,
                                thisAlbum,
                                thisArtistsId,
                                thisArtist,
                                null
                            )
                        }
                    } while (it.moveToNext())
                it.close()
            }
            return songList
        }


    }
}
