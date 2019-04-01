package com.projetdev.malo.musichall.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projetdev.malo.musichall.Activitutils
import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.models.Result
import kotlinx.android.synthetic.main.list_row.view.*

import java.util.ArrayList

class MainAdapter internal constructor(private var items: ArrayList<Result>, context: Context) : RecyclerView.Adapter<ViewHolder>() {

        private var context: Context

        init {
            this.context = context
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_row, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Log.e("MERDENIO", items[position].thumbnail)
            holder.textView.text = items[position].name
            holder.imageView.setImageDrawable(Activitutils.loadImageFromWebOperations(items[position].thumbnail))

        }

        override fun getItemCount(): Int {
            return items.size
        }

        fun refreshList(newList: ArrayList<Result>) {
            items = newList
            notifyDataSetChanged()
        }
    }

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val textView    = view.search_textview_title
    val imageView   = view.search_imageview
}
