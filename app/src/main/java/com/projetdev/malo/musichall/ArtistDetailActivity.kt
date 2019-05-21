package com.projetdev.malo.musichall

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
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

        val artist: Artist? = api.getArtist(data.getIntExtra("id",0))

        Picasso.get()
            .load(data.getStringExtra("thumb"))
            .into(findViewById<ImageView>(R.id.cover_image))

        findViewById<TextView>(R.id.artist_detail_title).text = artist?.name
        findViewById<TextView>(R.id.artist_detail_desc).text  = artist?.summup
    }
}