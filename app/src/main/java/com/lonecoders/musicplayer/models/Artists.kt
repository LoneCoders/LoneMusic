package com.lonecoders.musicplayer.models

import android.content.Context
import android.database.Cursor
import android.os.Parcelable
import android.provider.MediaStore
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Parcelize
data class Artists(
    val albumName: String,
    val artistId: Long,
    val artistName : String
):Parcelable {

    companion object {
        suspend fun getArtists(cursor: Cursor?, c: Context): MutableList<Artists> {
            return withContext(Dispatchers.IO) {
                val list = mutableListOf<Artists>()
                cursor?.use {
                    it.moveToFirst()
                    val artistId = it.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST_ID)
                    val albumNameColumn = it.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM)
                    val artistsNameColumn = it.getColumnIndex(MediaStore.Audio.ArtistColumns.ARTIST)
                    do {
                        val thisArtistsId = it.getLong(artistId)
                        val thisAlbumName = it.getString(albumNameColumn)
                        val thisArtistName = it.getString(artistsNameColumn)
                        list += Artists(
                            thisAlbumName,
                            thisArtistsId,
                            thisArtistName
                        )
                    } while (it.moveToNext())
                }
                list
            }
        }
    }
}

