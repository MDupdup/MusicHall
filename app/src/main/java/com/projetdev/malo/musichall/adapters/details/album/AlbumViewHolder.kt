package com.projetdev.malo.musichall.adapters.details.album

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.row_list_main_rv.view.*

class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val row = view.search_row
    val title = view.search_textview_title
    val imageView = view.search_imageview
    val year = view.search_textview_year
}