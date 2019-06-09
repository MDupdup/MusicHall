package com.projetdev.malo.musichall.adapters.details.album

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.projetdev.malo.musichall.Utils.Activitutils
import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.models.Album
import com.squareup.picasso.Picasso

import java.util.ArrayList

class AlbumAdapter internal constructor(private var items: ArrayList<Album>, private var context: Context) :
    RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {

        return AlbumViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list_disc_collection_fragment, parent, false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.title.text = items[position].name
        holder.imageView.setImageDrawable(Activitutils.loadImageFromWebOperations(items[position].images?.get("large")!!))


        Picasso.get()
            .load(items[position].images.get("large")!!)
            .placeholder(R.drawable.ic_image_black_512dp)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun refreshList(newList: ArrayList<Album>) {
        items = newList
        notifyDataSetChanged()
    }
}
