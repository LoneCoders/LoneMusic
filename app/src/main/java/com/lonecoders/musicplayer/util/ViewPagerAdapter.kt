package com.lonecoders.musicplayer.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lonecoders.musicplayer.fragments.AlbumFragment
import com.lonecoders.musicplayer.fragments.ArtistsFragment
import com.lonecoders.musicplayer.fragments.PlaylistsFragment
import com.lonecoders.musicplayer.fragments.SongsFragment

class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> SongsFragment()
            1 -> AlbumFragment()
            2 -> ArtistsFragment()
            3 -> PlaylistsFragment()
            else -> SongsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        super.getPageTitle(position)
        return when(position){
            0 -> "Songs"
            1 -> "Album"
            2 -> "Artists"
            3 -> "Playlists"
            else -> ""
        }
    }
}
