package com.projetdev.malo.musichall.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.projetdev.malo.musichall.AlbumDetailActivity;
import com.projetdev.malo.musichall.ArtistDetailActivity;
import com.projetdev.malo.musichall.R;
import com.projetdev.malo.musichall.Utils.Activitutils;
import com.projetdev.malo.musichall.models.Album;
import com.projetdev.malo.musichall.models.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context context;
    private ArrayList<Item> items;

    public MainAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list_main_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.search_textview_title.setText(items.get(position).getName());
        holder.search_image.setImageDrawable(Activitutils.Companion.loadImageFromWebOperations(items.get(position).getImages().get("large")));

        System.out.println("euuuuuuuuuh " + items.get(position));

        if (items.get(position).getImages().get("large").isEmpty()) {
            holder.search_image.setImageResource(R.drawable.ic_image_black_512dp);
        } else {
            Picasso.get()
                    .load(items.get(position).getImages().get("large"))
                    .placeholder(R.drawable.ic_image_black_512dp)
                    .into(holder.search_image);
        }

        if(items.get(position) instanceof Album) {
            holder.textview_year.setText(Integer.toString(items.get(position).getYear()));
            holder.textview_artist.setText(items.get(position).getArtist().getName());
        } else {
            holder.textview_year.setText(items.get(position).getPlayCount() + " écoutes");
            holder.textview_artist.setText("");
        }

        holder.row.setOnClickListener(v -> {
            Class detail = items.get(position) instanceof Album ? AlbumDetailActivity.class : ArtistDetailActivity.class;
            Intent intent = new Intent(context, detail);
            intent.putExtra("id", items.get(position).getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void refreshList(ArrayList<Item> newList) {
        items = newList;
        notifyDataSetChanged();
    }
}
