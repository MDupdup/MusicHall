package com.projetdev.malo.musichall.adapters.details.album

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.row_list_disc_collection_fragment.view.*

class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val row = view.album_collection_row
    val title = view.album_collection_title
    val imageView = view.album_collection_image
    val trash = view.album_collection_delete
}