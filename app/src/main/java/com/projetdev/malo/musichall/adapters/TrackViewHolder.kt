package com.projetdev.malo.musichall.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.track_list_item.view.*

class TrackViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val title = v.track_title
    val duration = v.track_duration
    val position = v.track_position
}