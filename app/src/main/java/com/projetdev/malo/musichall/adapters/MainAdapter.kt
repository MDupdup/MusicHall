package com.projetdev.malo.musichall.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projetdev.malo.musichall.Utils.Activitutils
import com.projetdev.malo.musichall.ArtistDetailActivity
import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.models.Artist
import com.projetdev.malo.musichall.models.Disc
import com.projetdev.malo.musichall.models.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_list_main_rv.view.*

import java.util.ArrayList

class MainAdapter internal constructor(private var items: ArrayList<Result>, context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    private var context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list_main_rv, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("MERDENIO", items[position].thumbnail)
        holder.title.text = items[position].name
        holder.imageView.setImageDrawable(Activitutils.loadImageFromWebOperations(items[position].thumbnail))

        if(items[position] is Artist) {
            //items.containsAll()
        } else if(items[position] is Disc) {
            holder.subtitle.text = (items[position] as Disc)
        }

        Picasso.get()
            .load(items[position].thumbnail)
            .placeholder(R.drawable.ic_image_black_512dp)
            .into(holder.imageView)

        holder.row.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, ArtistDetailActivity::class.java)
                intent.putExtra("id", items[position].id)
                intent.putExtra("thumb", items[position].thumbnail)

                context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun refreshList(newList: ArrayList<Result>) {
        items = newList
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val row = view.search_row
    val title = view.search_textview_title
    val imageView = view.search_imageview
    val year = view.search_textview_year
    val subtitle = view.search_textview_subtitle

}
