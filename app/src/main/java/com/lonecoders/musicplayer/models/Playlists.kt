package com.lonecoders.musicplayer.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playLists")
data class Playlists(
    @PrimaryKey(autoGenerate = true) val id : Long = 0L,
    @ColumnInfo(name = "Name") val name : String,
    @ColumnInfo(name = "Songs")
    val songs : List<Song>? = null,
    @ColumnInfo(name="nofsongs")
    val nof : Int? = null
)