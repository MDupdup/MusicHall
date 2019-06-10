package com.projetdev.malo.musichall.Utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.projetdev.malo.musichall.R
import java.io.InputStream
import java.net.URL

class Activitutils {

    companion object {
        fun loadImageFromWebOperations(url: String?): Drawable? {
            try {
                val `is` = URL(url).getContent() as InputStream
                return Drawable.createFromStream(`is`, "src name")
            } catch (e: Exception) {
                Log.e("Utils", "$e")
                return null
            }
        }

        fun createTag(context: Context, text: String): TextView {
            val tag = TextView(context)
            tag.background = context.getDrawable(R.drawable.style_tag)
            tag.setPadding(4, 4, 4, 4)
            tag.setTextColor(Color.WHITE)
            tag.text = text

            val params = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(5, 0, 0, 0)

            tag.layoutParams = params

            return tag
        }
    }
}
