package com.projetdev.malo.musichall

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.projetdev.malo.musichall.Utils.Constant
import com.projetdev.malo.musichall.adapters.details.album.TrackAdapter
import com.projetdev.malo.musichall.api.ApiCall
import com.projetdev.malo.musichall.models.Album
import com.squareup.picasso.Picasso

class AlbumDetailActivity : AppCompatActivity() {

    private val api: ApiCall = ApiCall(Constant.ADDRESS)

    private lateinit var album: Album

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var favButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_album)

        val data = intent

        try {

            album = if (data.extras.containsKey("artist")) api.getAlbum(
                data.getStringExtra("id"),
                "/" + data.getStringExtra("artist")
            )!!
            else api.getAlbum(data.getStringExtra("id"))!!

            Picasso.get()
                .load(album.images["large"])
                .into(findViewById<ImageView>(R.id.album_cover_image))

            findViewById<TextView>(R.id.artist_detail_title).text = album.name
            findViewById<TextView>(R.id.artist_detail_desc).text = album.summup

            viewManager = LinearLayoutManager(this)
            recyclerView = findViewById<RecyclerView>(R.id.track_list).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = TrackAdapter(
                    album.tracks,
                    this@AlbumDetailActivity
                )
            }

            favButton = findViewById<ImageView>(R.id.album_add_favs)
            val isInDB = api.isInDB(album.name, "album")
            if (isInDB) {
                Log.i("Bool", "It's $isInDB")
                favButton.setImageResource(R.drawable.ic_star_white_36dp)
            }

            val tagsView: LinearLayout = findViewById(R.id.album_taglist)

            for (tag in album.tags!!) {
                val tagView = TextView(this@AlbumDetailActivity)
                tagView.setPadding(5, 5, 5, 5)
                tagView.text = tag
                tagView.setTextColor(Color.WHITE)
                tagView.textSize = 13f
                tagView.setBackgroundResource(R.drawable.style_tag)
                ViewCompat.setElevation(tagView, 5f)

                tagsView.addView(tagView)
            }
        } catch (e: Exception) {
            Log.e("ERROR", "Error while loading the album", e)
            finishAfterTransition()
        }
    }


    fun addToFavorites(v: View) {
        if (api.isInDB(album.name, "album")) {
            favButton.setImageResource(R.drawable.ic_star_border_white_36dp)
            api.delete(album.name, "album")
        } else {
            favButton.setImageResource(R.drawable.ic_star_white_36dp)
            api.insertAlbum(album)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finishAfterTransition()
        }
        return true
    }
}