package com.lonecoders.musicplayer.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lonecoders.musicplayer.models.Song


class TypeConverters {


    @TypeConverter
    fun songListToString(someObjects: List<Song>): String? {
        val listType = object : TypeToken<List<Song>>(){}.type
        return Gson().toJson(someObjects,listType)
    }
    @TypeConverter
    fun stringToSong(data : String):List<Song>{

        val listType = object : TypeToken<List<Song>>(){}.type

        return Gson().fromJson(data,listType)

    }



}