package com.projetdev.malo.musichall.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.models.Track

class TrackAdapter internal constructor(private var items: ArrayList<Track>, private var context: Context) :
    RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {

        return TrackViewHolder(LayoutInflater.from(context).inflate(R.layout.track_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.duration.text = toTime(items[position].duration)
        holder.position.text = items[position].position
    }

    private fun toTime(secondsAmount: Int): CharSequence? {
        val minutes: Int = secondsAmount / 60
        val seconds: Int = secondsAmount % 60

        if (seconds <= 9)
            return "$minutes:0$seconds"
        else
            return "$minutes:$seconds"


    }

    override fun getItemCount(): Int {
        return items.size
    }
}