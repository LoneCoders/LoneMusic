package com.lonecoders.musicplayer.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lonecoders.musicplayer.fragments.AlbumFragment
import com.lonecoders.musicplayer.fragments.ArtistsFragment
import com.lonecoders.musicplayer.fragments.PlaylistsFragment
import com.lonecoders.musicplayer.fragments.SongsFragment

class ViewPager2Adapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SongsFragment()
            1 -> AlbumFragment()
            2 -> ArtistsFragment()
            3 -> PlaylistsFragment()
            else -> SongsFragment()
        }
    }

}