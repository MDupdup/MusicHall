package com.projetdev.malo.musichall.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.models.Artist

class Adapter(private val dataset: ArrayList<Artist>): RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_row, parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun getItemCount() = dataset.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataset[position].name
    }
}