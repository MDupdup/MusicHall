package com.projetdev.malo.musichall.adapters.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.projetdev.malo.musichall.R;

class MainViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout row;
    public TextView search_textview_title;
    public ImageView search_image;
    public TextView textview_year;
    public TextView textview_artist;


    public MainViewHolder(View v) {
        super(v);

        this.search_textview_title = v.findViewById(R.id.search_textview_title);
        this.row = v.findViewById(R.id.search_row);
        this.search_image = v.findViewById(R.id.search_imageview);
        this.textview_year = v.findViewById(R.id.search_textview_year);
        this.textview_artist = v.findViewById(R.id.search_textview_artist);
    }
}
