package com.projetdev.malo.musichall

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.projetdev.malo.musichall.Utils.Constant
import com.projetdev.malo.musichall.api.ApiCall
import com.projetdev.malo.musichall.models.Artist
import com.squareup.picasso.Picasso

class ArtistDetailActivity : AppCompatActivity() {

    val api: ApiCall = ApiCall(Constant.ADDRESS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artist)
        Log.e("DEBUG", "Entering details")

        val data = intent

        val artist: Artist? = api.getArtist(data.getStringExtra("id"))

        Picasso.get()
            .load(artist!!.images["large"])
            .into(findViewById<ImageView>(R.id.cover_image))

        findViewById<TextView>(R.id.artist_detail_title).text = artist.name
        findViewById<TextView>(R.id.artist_detail_desc).text  = artist.summup

        /*val tagsView = findViewById<LinearLayout>(R.id.artist_taglist)

        for (tag in artist.tags!!) {
            val tagView = TextView(this@ArtistDetailActivity)
            tagView.text = tag
            tagView.setTextColor(Color.WHITE)
            tagView.textSize = 13f
            tagView.setBackgroundResource(R.drawable.style_tag)
            ViewCompat.setElevation(tagView, 5f)

            tagsView.addView(tagView)
        }*/
    }
}