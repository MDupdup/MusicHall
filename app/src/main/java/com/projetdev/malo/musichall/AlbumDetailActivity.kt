package com.projetdev.malo.musichall

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.*
import com.projetdev.malo.musichall.Utils.Constant
import com.projetdev.malo.musichall.adapters.details.album.TrackAdapter
import com.projetdev.malo.musichall.api.ApiCall
import com.projetdev.malo.musichall.models.Album
import com.squareup.picasso.Picasso

class AlbumDetailActivity : AppCompatActivity() {

    val api: ApiCall = ApiCall(Constant.ADDRESS)

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_album)
        Log.e("DEBUG", "Entering details")

        val data = intent

        val album: Album? = api.getAlbum(data.getStringExtra("id"))

        Picasso.get()
            .load(data.getStringExtra("large"))
            .into(findViewById<ImageView>(R.id.cover_image))

        findViewById<TextView>(R.id.artist_detail_title).text = album?.name
        findViewById<TextView>(R.id.artist_detail_desc).text = album?.summup

        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.track_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = TrackAdapter(
                album!!.tracks,
                this@AlbumDetailActivity
            )
        }


        val tagsView: LinearLayout = findViewById(R.id.taglist)

        for (tag in album?.tags!!) {
            val tagView = TextView(this@AlbumDetailActivity)
            tagView.text = tag
            tagView.setTextColor(Color.WHITE)
            tagView.textSize = 13f
            tagView.setBackgroundResource(R.drawable.style_tag)
            ViewCompat.setElevation(tagView, 5f)

            tagsView.addView(tagView)
        }

    }
}