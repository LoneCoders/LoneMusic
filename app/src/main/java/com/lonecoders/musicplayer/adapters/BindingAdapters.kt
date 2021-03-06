package com.lonecoders.musicplayer.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lonecoders.musicplayer.R
import com.lonecoders.musicplayer.models.Album
import com.lonecoders.musicplayer.models.Song

@BindingAdapter("songAlbumCover")
fun ImageView.setAlbumCover(song: Song) {
    Glide.with(this.context)
        .load(song.songAlbumCoverUri)
        .placeholder(R.drawable.ic_song_cover_default)
        .centerCrop()
        .into(this)
}

@BindingAdapter("songName")
fun TextView.setSongName(song: Song) {
    text = song.songName
}

@BindingAdapter("songInfo")
fun TextView.setSongInfo(song: Song) {
    val songInfo = "${song.artistName} - ${song.albumName}"
    text = songInfo
}

@BindingAdapter("albumCover")
fun ImageView.setAlbumCover(album: Album) {
    Glide.with(this.context)
        .load(album.albumCoverUri)
        .placeholder(R.drawable.ic_album_cover_default)
        .centerCrop()
        .into(this)
}

@BindingAdapter("albumName")
fun TextView.setAlbumName(album: Album) {
    text = album.albumName
}