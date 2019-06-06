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
import android.widget.*
import com.github.clans.fab.FloatingActionMenu
import com.projetdev.malo.musichall.Utils.Animator.Companion.hideSearchBar
import com.projetdev.malo.musichall.Utils.Animator.Companion.showSearchBar
import com.projetdev.malo.musichall.Utils.Constant
import com.projetdev.malo.musichall.adapters.main.MainAdapter
import com.projetdev.malo.musichall.api.ApiCall
import com.projetdev.malo.musichall.adapters.main.MainRVDecorator
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {

    private lateinit var api: ApiCall

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var menuFab: FloatingActionMenu

    private lateinit var mainAdapter: MainAdapter

    private var initTimer: Boolean = true
    private var isTimerActive: Boolean = false
    private var handler: Handler = Handler()
    private lateinit var timer: TimerTask

    private var isSearchShown: Boolean = true

    companion object {
        var searchMode: Int = Constant.BOTH
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api = ApiCall(Constant.ADDRESS)

        menuFab = findViewById(R.id.menu_fab)

        val searchBar = findViewById<SearchView>(R.id.searchAutoComplete)
        searchBar.isFocusedByDefault = false

        val switchSearchButton = findViewById<ImageView>(R.id.switch_search_button)


        // RecyclerView Initialization
        mainAdapter = MainAdapter(api.search(searchMode), this)

        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = mainAdapter
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


        // Popup Filter Menu
        switch_search_button.setOnClickListener {
            val popupMenu = PopupMenu(this@MainActivity, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.filter_both_option -> {
                        searchMode = Constant.BOTH
                        findViewById<ImageView>(R.id.background_soshoko_img).setImageResource(R.drawable.ic_music_note_black_512dp)
                        findViewById<TextView>(R.id.background_soshoko_txt).text = "Search for albums or artists!"
                        true
                    }
                    R.id.filter_artist_only_option -> {
                        searchMode = Constant.ARTIST
                        findViewById<ImageView>(R.id.background_soshoko_img).setImageResource(R.drawable.ic_group_black_512dp)
                        findViewById<TextView>(R.id.background_soshoko_txt).text = "Search for artists!"
                        true
                    }
                    R.id.filter_album_only_option -> {
                        searchMode = Constant.ALBUM
                        findViewById<ImageView>(R.id.background_soshoko_img).setImageResource(R.drawable.ic_album_black_256dp)
                        findViewById<TextView>(R.id.background_soshoko_txt).text = "Search for albums!"
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.menu_filter)

            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(mPopup, true)
            } catch (e: Exception) {
                Log.e("Ohhh", "Menu Icons Error...", e)
            } finally {
                popupMenu.show()
            }

        }


        // Loading Progress Bar
        val searchLoading: ProgressBar = findViewById(R.id.search_loading)
        searchLoading.visibility = View.GONE


        // Loading Background
        val backgroundLayout = findViewById<LinearLayout>(R.id.background_soshoko)
        val backgroundImg = findViewById<ImageView>(R.id.background_soshoko_img)
        val backgroundTxt = findViewById<TextView>(R.id.background_soshoko_txt)
        backgroundImg.setImageResource(R.drawable.ic_music_note_black_512dp)
        backgroundTxt.text = "Search for albums or artists!"

        // Search Bar Initialization
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                isSearchShown = hideSearchBar(searchBar, switchSearchButton)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("Searching", newText)

                isTimerActive = true
                if (!initTimer) timer.cancel()

                timer = Timer("SearchApi", false).schedule(300) {

                    runOnUiThread {
                        searchLoading.visibility = View.VISIBLE
                        backgroundLayout.visibility = View.GONE
                    }

                    if (isTimerActive) {
                        timer.cancel()
                    }
                    isTimerActive = false
                    initTimer = false

                    val queryList = api.search(searchMode, newText?.replace(" ", "%20"))
                    handler.post {
                        mainAdapter.refreshList(queryList)
                        recyclerView.adapter = mainAdapter

                        runOnUiThread {
                            searchLoading.visibility = View.GONE
                            if (newText!! == "") backgroundLayout.visibility = View.VISIBLE
                        }
                    }
                }
                return true
            }
        })
    }

    fun getToCollection(view: View) {
        val intent = Intent(this, CollectionActivity::class.java)

        startActivity(intent)
    }
}