package com.projetdev.malo.musichall.models

import com.projetdev.malo.musichall.models.Track

class Album(
    val id:         String,
    val title:      String,
    val url:        String,
    val artist:     Artist,
    val images:     Map<String,String>
) {
    constructor(
        id:         String,
        title:      String,
        url:        String,
        artist:     Artist,
        images:     Map<String,String>,
        tracks:     ArrayList<Track>?,
        tags:       ArrayList<String>?,
        summup:     String?,
        content:    String?
    ) : this(id, title, url, artist, images)
}