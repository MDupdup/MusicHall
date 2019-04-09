package com.projetdev.malo.musichall

import android.animation.ValueAnimator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.support.v7.widget.SearchView
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import com.github.clans.fab.FloatingActionMenu
import com.projetdev.malo.musichall.api.ApiCall
import com.projetdev.malo.musichall.adapters.MainAdapter
import com.projetdev.malo.musichall.adapters.MainRVDecorator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private lateinit var api: ApiCall

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var menuFab: FloatingActionMenu

    private lateinit var myAdapter: MainAdapter

    private var initTimer: Boolean = true
    private var isTimerActive: Boolean = false
    private var handler: Handler = Handler()
    private lateinit var timer: TimerTask

    private var isSearchShown: Boolean = true

    private var searchMode: Int = Constant.ARTIST


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api = ApiCall(Constant.ADDRESS)

        menuFab = findViewById(R.id.menu_fab)

        val searchBar = findViewById<SearchView>(R.id.searchAutoComplete)
        searchBar.isFocusedByDefault = false

        val switchSearchButton = findViewById<ImageView>(R.id.switch_search_button)

        myAdapter = MainAdapter(api.search(Constant.ARTIST), this@MainActivity)

        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = myAdapter
            addItemDecoration(MainRVDecorator(150))
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0 && isSearchShown) {
                    isSearchShown = hideSearchBar(searchBar, switchSearchButton)
                } else if (dy < 0 && !isSearchShown) {
                    isSearchShown = showSearchBar(searchBar, switchSearchButton)
                }
            }
        })

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                isSearchShown = hideSearchBar(searchBar, switchSearchButton)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("MDR", newText)

                isTimerActive = true
                if (!initTimer) timer.cancel()

                timer = Timer("SearchApi", false).schedule(300) {
                    if (isTimerActive) {
                        timer.cancel()
                    }
                    isTimerActive = false
                    initTimer = false

                    val queryList = api.search(searchMode, newText?.replace(" ", "%20"))
                    handler.post {
                        myAdapter.refreshList(queryList)
                    }
                }
                return true
            }
        })
    }


    fun switchSearch(view: View) {
        searchMode = if(searchMode == 1) 0 else 1

        val newList = api.search(searchMode)
        handler.post {
            myAdapter.refreshList(newList)
        }
    }

    fun getToCollection(view: View) {
        val intent = Intent(this, CollectionActivity::class.java)

        startActivity(intent)
    }

    private fun hideSearchBar(searchBar: SearchView, switchSearchButton: ImageView, speed: Long = 250): Boolean {
        val valueAnimator = ValueAnimator.ofFloat(0f, -200f)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            searchBar.translationY = value
            switchSearchButton.translationY = value
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = speed

        valueAnimator.start()

        return false
    }

    private fun showSearchBar(searchBar: SearchView, switchSearchButton: ImageView, speed: Long = 250): Boolean {
        val valueAnimator = ValueAnimator.ofFloat(-200f, 0f)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            searchBar.translationY = value
            switchSearchButton.translationY = value
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = speed

        valueAnimator.start()

        return true
    }


    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        //TODO: Implement saveInstanceState!
    }
}