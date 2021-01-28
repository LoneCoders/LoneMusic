package com.lonecoders.musicplayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lonecoders.musicplayer.models.Playlists

@Database(entities =  [Playlists::class],version = 1,exportSchema = false)
@TypeConverters(com.lonecoders.musicplayer.util.TypeConverters::class)
abstract class PlayListsDb : RoomDatabase() {
    abstract val playListsDao : PlayListsDao
    companion object {
        @Volatile
        private var INSTANCE: PlayListsDb? = null

        fun getInstance(ctx: Context): PlayListsDb {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(ctx, PlayListsDb::class.java, "lone-music.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}