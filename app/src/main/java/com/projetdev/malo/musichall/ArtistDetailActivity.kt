package com.projetdev.malo.musichall

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.projetdev.malo.musichall.Utils.Constant
import com.projetdev.malo.musichall.api.ApiCall
import com.projetdev.malo.musichall.models.Artist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_artist.*


class ArtistDetailActivity : AppCompatActivity() {

    val api: ApiCall = ApiCall(Constant.ADDRESS)

    lateinit var artist: Artist

    lateinit var favButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artist)
        Log.e("DEBUG", "Entering details")

        val data = intent

        try {
            artist = api.getArtist(data.getStringExtra("id"))!!

            Picasso.get()
                .load(artist.images["large"])
                .into(findViewById<ImageView>(R.id.cover_image))


            findViewById<TextView>(R.id.artist_detail_title).text = artist.name
            findViewById<TextView>(R.id.artist_detail_desc).text = artist.summup

            favButton = findViewById(R.id.artist_add_favs)
            if (api.isInDB(artist.name, "artist")) {
                favButton.setImageResource(R.drawable.ic_star_white_36dp)
            }

            if (artist.onTour) {
                artist_detail_isontour.visibility = View.VISIBLE
            } else {
                artist_detail_isontour.visibility = View.GONE
            }

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
        } catch (e: Exception) {
            Log.e("ERROR", "Error while loading the artist", e)
            finishAfterTransition()
        }

    }

    fun addToFavorites(v: View) {
        if (api.isInDB(artist.name, "artist")) {
            favButton.setImageResource(R.drawable.ic_star_border_white_36dp)
            api.delete(artist.name, "artist")
        } else {
            favButton.setImageResource(R.drawable.ic_star_white_36dp)
            api.insertArtist(artist)
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