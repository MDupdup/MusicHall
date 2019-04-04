package com.projetdev.malo.musichall.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.projetdev.malo.musichall.fragments.collection.ArtistCollectionFragment
import com.projetdev.malo.musichall.fragments.collection.DiscCollectionFragment


class CollectionPagerAdapter(fragmentManager: FragmentManager?) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? {
        when(position) {
            0 -> return ArtistCollectionFragment()
            1 -> return DiscCollectionFragment()
        }
        return null
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        when(position) {
            0 -> return "Artists"
            1 -> return "Discs"
        }
        return ""
    }
}