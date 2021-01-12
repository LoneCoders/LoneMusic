package com.lonecoders.musicplayer.util
import com.lonecoders.musicplayer.models.Album
import com.lonecoders.musicplayer.models.Artists

class FormatList{
    fun formatArtists(list : MutableList<Artists>) : MutableList<Artists>{
        var found: Boolean
        val newlist = mutableListOf<Artists>()
        for( item in list){
            found = false
            for(newItem in newlist){
                if(newItem.artistName == item.artistName)
                    found = true
            }
            if(!found)
                newlist.add(item)


        }
        return newlist
    }
    fun formatAlbum(list: MutableList<Album>) : MutableList<Album>{
        var found : Boolean
        val newlist = mutableListOf<Album>()
        for( item in list){
            found = false
            for(newItem in newlist){
                if(newItem.albumName == item.albumName)
                    found = true
            }
            if(!found)
                newlist.add(item)


        }
        return newlist
    }
}