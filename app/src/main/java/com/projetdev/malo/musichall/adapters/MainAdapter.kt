package com.projetdev.malo.musichall.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projetdev.malo.musichall.Utils.Activitutils
import com.projetdev.malo.musichall.ArtistDetailActivity
import com.projetdev.malo.musichall.MainActivity
import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.Utils.Constant
import com.projetdev.malo.musichall.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_list_main_rv.view.*

import java.util.ArrayList

class MainAdapter internal constructor(private var items: ArrayList<Item>, context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    private var context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list_main_rv, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].name
        holder.imageView.setImageDrawable(Activitutils.loadImageFromWebOperations(items[position].images?.get("medium")!!))

        if(MainActivity.searchMode == Constant.ARTIST) {
            //items.containsAll()
        } else if(MainActivity.searchMode == Constant.RELEASE) {
            //holder.year.text = items[position].year


            holder.taglist.removeAllViews()

            var i = 0
            /*items[position].style?.forEach {
                if(i == 3) return
                holder.taglist.addView(Activitutils.createTag(context, it))
                i++
            }*/
        }

        Picasso.get()
            .load(items[position].images?.get("medium")!!)
            .placeholder(R.drawable.ic_image_black_512dp)
            .into(holder.imageView)

        holder.row.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, ArtistDetailActivity::class.java)
                intent.putExtra("id", items[position].id)

                context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun refreshList(newList: ArrayList<Item>) {
        items = newList
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val row = view.search_row
    val title = view.search_textview_title
    val imageView = view.search_imageview
    val year = view.search_textview_year
    //val subtitle = view.search_textview_subtitle
    val taglist = view.taglist
}
