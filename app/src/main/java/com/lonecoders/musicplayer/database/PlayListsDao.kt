package com.lonecoders.musicplayer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lonecoders.musicplayer.models.Playlists

@Dao
@TypeConverters(com.lonecoders.musicplayer.util.TypeConverters::class)
interface PlayListsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(playlists: Playlists)
    @Update
    fun update(playlists: Playlists)
    @Query("SELECT * FROM playLists")
    fun getAll() : LiveData<List<Playlists>>
    @Query("SELECT * FROM PLAYLISTS WHERE ID = :id")
    fun getByID(id : Long) : LiveData<Playlists>
}