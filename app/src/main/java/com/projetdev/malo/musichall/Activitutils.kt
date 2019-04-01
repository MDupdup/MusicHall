package com.projetdev.malo.musichall

import android.graphics.drawable.Drawable
import java.io.InputStream
import java.net.URL

class Activitutils {

    companion object {
        fun loadImageFromWebOperations(url: String): Drawable? {
            try {
                val `is` = URL(url).getContent() as InputStream
                return Drawable.createFromStream(`is`, "src name")
            } catch (e: Exception) {
                return null
            }

        }
    }
}
