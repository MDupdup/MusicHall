package com.projetdev.malo.musichall

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.projetdev.malo.musichall.adapters.CollectionPagerAdapter
import com.projetdev.malo.musichall.fragments.collection.ArtistCollectionFragment
import com.projetdev.malo.musichall.fragments.collection.DiscCollectionFragment

class CollectionActivity: AppCompatActivity(), ArtistCollectionFragment.OnFragmentInteractionListener, DiscCollectionFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val pagerAdapter = CollectionPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tablayout)
        tabLayout.setupWithViewPager(viewPager)
    }

}