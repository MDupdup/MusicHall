package com.projetdev.malo.musichall.adapters.details.artist

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.row_list_artist_collection_fragment.view.*

class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val row = view.artist_collection_row
    val title = view.artist_collection_title
    val imageView = view.artist_collection_image
    val trash = view.artist_collection_delete
}