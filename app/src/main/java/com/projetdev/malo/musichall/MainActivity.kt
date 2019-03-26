package com.projetdev.malo.musichall

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import com.github.clans.fab.FloatingActionMenu
import com.projetdev.malo.musichall.api.ApiCall
import com.projetdev.malo.musichall.models.Artist
import com.projetdev.malo.musichall.recyclerview.Adapter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var menuFab: FloatingActionMenu

    private lateinit var queryList: ArrayList<Artist>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = ApiCall("http://10.44.0.227:3333")

        menuFab = findViewById(R.id.menu_fab)

        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = Adapter(api.searchForArtists("Muse"), this@MainActivity)
        }



        /*val searchBar = findViewById<SearchView>(R.id.search_bar)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryList = api.searchForArtists(query)
                recyclerView.adapter = Adapter(queryList, this@MainActivity)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("MDR", newText)
                return true
            }
        })*/

        //Log.i("MDR", api.getRelease(645846)?.name)
    }



    private fun refreshList(newList: ArrayList<Artist>) {

    }

    fun getToCollection(view: View) {
        val intent = Intent(this, CollectionActivity::class.java)

        startActivity(intent)
    }
}