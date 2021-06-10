package com.lonecoders.musicplayer.models

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media.*
import kotlinx.parcelize.Parcelize


@Parcelize
data class Song(
    val songId: Long,
    val songName: String,
    val albumName: String,
    val albumId: Long,
    val artistName: String,
    val songAlbumCoverUri: String?,
    val songUri : String
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
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        thisSongId
                    )
                    songList += Song(
                        thisSongId,
                        thisSongTitle,
                        thisSongAlbum,
                        thisSongAlbumId,
                        thisSongArtist,
                        thisSongAlbumCoverUri.toString(),
                        contentUri.toString()
                    )
                }
                it.close()
            }

            return songList

        }

        fun getSongsFromCursor(
            cursor: Cursor?,
            album: String
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
                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            thisSongId
                        )
                        if (thisAlbum == album) {


                            songList += Song(
                                thisSongId,
                                thisSongTitle,
                                thisAlbum,
                                thisAlbumId,
                                thisArtist,
                                thisSongAlbumCoverUri.toString(),
                                contentUri.toString()
                            )
                        }
                    } while (it.moveToNext())
                it.close()
            }
            return songList
        }

        fun getSongsFromCursor(artists : String, cursor : Cursor?) : MutableList<Song>{
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
                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            thisSongId
                        )
                        if (thisArtist == artists) {


                            songList += Song(
                                thisSongId,
                                thisSongTitle,
                                thisAlbum,
                                thisArtistsId,
                                thisArtist,
                                null,
                                contentUri.toString()
                            )
                        }
                    } while (it.moveToNext())
                it.close()
            }
            return songList
        }


    }
}
