package com.lonecoders.musicplayer.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lonecoders.musicplayer.models.Song


class TypeConverters {
    val gson = Gson()

    @TypeConverter
    fun stringToSong(data : String):List<Song>{

        val listType = object : TypeToken<List<Song?>?>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun songListToString(someObjects: List<Song?>?): String? {
        return gson.toJson(someObjects)
    }
}