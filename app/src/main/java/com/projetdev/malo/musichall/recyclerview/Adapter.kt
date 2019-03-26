package com.projetdev.malo.musichall.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.models.Artist
import kotlinx.android.synthetic.main.list_row.view.*

class Adapter(val items : ArrayList<Artist>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.textView?.text = items.get(position).name
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.mdr
}