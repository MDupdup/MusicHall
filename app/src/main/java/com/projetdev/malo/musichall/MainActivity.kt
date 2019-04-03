package com.projetdev.malo.musichall

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.support.v7.widget.SearchView
import android.view.MotionEvent
import com.github.clans.fab.FloatingActionMenu
import com.projetdev.malo.musichall.api.ApiCall
import com.projetdev.malo.musichall.recyclerview.MainAdapter
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), RecyclerView.OnItemTouchListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var menuFab: FloatingActionMenu

    private lateinit var myAdapter: MainAdapter

    private var initTimer: Boolean = true
    private var handler: Handler = Handler()


    private val countDownLatch: CountDownLatch = CountDownLatch(1)


    private lateinit var timer: TimerTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = ApiCall("http://192.168.1.11:3333")

        menuFab = findViewById(R.id.menu_fab)

        myAdapter = MainAdapter(api.searchForArtists(), this@MainActivity)

        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = myAdapter
            addOnItemTouchListener(this@MainActivity)
        }

        val searchBar = findViewById<SearchView>(R.id.searchAutoComplete)
        searchBar.isFocusedByDefault = false

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val queryList = api.searchForArtists(query)
                myAdapter.refreshList(queryList)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("MDR", newText)

                if (!initTimer) timer.cancel()

                timer = Timer("SearchApi", false).schedule(250) {
                    if (initTimer) {
                        initTimer = false
                    }

                    val queryList = api.searchForArtists(newText?.replace(" ", "%20"))
                    handler.post {
                        myAdapter.refreshList(queryList)
                    }
                }
                return true
            }
        })

        //Log.i("MDR", api.getRelease(645846)?.name)
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun getToCollection(view: View) {
        val intent = Intent(this, CollectionActivity::class.java)

        startActivity(intent)
    }
}