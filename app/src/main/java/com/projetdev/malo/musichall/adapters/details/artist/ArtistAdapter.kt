package com.projetdev.malo.musichall.adapters.details.artist

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.projetdev.malo.musichall.ArtistDetailActivity
import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.Utils.Activitutils
import com.projetdev.malo.musichall.Utils.Constant
import com.projetdev.malo.musichall.api.ApiCall
import com.projetdev.malo.musichall.models.Artist
import com.squareup.picasso.Picasso
import java.util.*

class ArtistAdapter internal constructor(private var items: ArrayList<Artist>, private var context: Context) :
    RecyclerView.Adapter<ArtistViewHolder>() {

    private val api = ApiCall(Constant.ADDRESS)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {

        return ArtistViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_list_artist_collection_fragment,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.title.text = items[position].name
        holder.imageView.setImageDrawable(Activitutils.loadImageFromWebOperations(items[position].images?.get("extralarge")))
        holder.row.setBackgroundColor(Color.parseColor("#ffffff"))

        Picasso.get()
            .load(items[position].images?.get("extralarge"))
            .placeholder(R.drawable.ic_image_black_512dp)
            .into(holder.imageView)

        holder.trash.setOnClickListener {
            val newList = items
            newList.removeAt(position)
            notifyItemRemoved(position)
            refreshList(newList)
            api.delete(items[position].name, "artist")
        }

        holder.row.setOnClickListener {
            holder.row.setBackgroundColor(Color.parseColor("#c5c5c5"))
            val detail = ArtistDetailActivity::class.java
            val intent = Intent(context, detail)

            val options = ActivityOptions.makeSceneTransitionAnimation(
                context as Activity,
                holder.imageView,
                "background_image"
            )

            if (items[position].id == "") {
                intent.putExtra("id", items[position].name)
            } else
                intent.putExtra("id", items[position].id)

            context.startActivity(intent, options.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun refreshList(newList: ArrayList<Artist>) {
        items = newList
        notifyDataSetChanged()
    }
}
