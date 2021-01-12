package com.lonecoders.musicplayer.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayListsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(playlists: Playlists)
    @Update
    fun update(playlists: Playlists)
    @Query("SELECT * FROM playLists")
    fun getAll() : LiveData<List<Playlists>>
}