package com.lonecoders.musicplayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lonecoders.musicplayer.models.Song

@Entity(tableName = "playLists")
data class Playlists(
    @PrimaryKey(autoGenerate = true) val id : Long,
    @ColumnInfo(name = "Name") val name : String,
    @ColumnInfo(name = "Songs")
    val songs : List<Song>
)