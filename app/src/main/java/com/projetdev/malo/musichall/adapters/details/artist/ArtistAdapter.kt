package com.projetdev.malo.musichall.adapters.details.artist/*package com.projetdev.malo.musichall.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projetdev.malo.musichall.Utils.Activitutils
import com.projetdev.malo.musichall.ArtistDetailActivity
import com.projetdev.malo.musichall.MainActivity
import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.Utils.Constant
import com.projetdev.malo.musichall.models.Artist
import com.squareup.picasso.Picasso

import java.util.ArrayList

class ArtistAdapter internal constructor(private var items: ArrayList<Artist>, context: Context) :
    RecyclerView.Adapter<ArtistViewHolder>() {

    private var context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {

        return ArtistViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list_main_rv, parent, false))
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.title.text = items[position].name
        holder.imageView.setImageDrawable(Activitutils.loadImageFromWebOperations(items[position].images?.get("extralarge")))

        if(MainActivity.searchMode == Constant.ARTIST) {
            //items.containsAll()
        } else if(MainActivity.searchMode == Constant.RELEASE) {
            //holder.year.text = items[position].year


            holder.taglist.removeAllViews()

            var i = 0
            /*items[position].style?.forEach {
                if(i == 3) return
                holder.taglist.addView(Activitutils.createTag(context, it))
                i++
            }*/
        }

        Picasso.get()
            .load(items[position].images?.get("extralarge"))
            .placeholder(R.drawable.ic_image_black_512dp)
            .into(holder.imageView)

        holder.row.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, ArtistDetailActivity::class.java)
                intent.putExtra("id", items[position].id)

                context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun refreshList(newList: ArrayList<Artist>) {
        items = newList
        notifyDataSetChanged()
    }
}
*/