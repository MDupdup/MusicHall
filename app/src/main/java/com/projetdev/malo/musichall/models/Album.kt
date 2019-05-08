package com.projetdev.malo.musichall.models

import com.projetdev.malo.musichall.models.Track

class Album(
    override val id:        String,
    override val name:      String,
    override val url:       String,
    override val images:    Map<String,String>,
    val artist:             Artist
) : Item(id, name, url, images) {
    constructor(
        id:         String,
        name:      String,
        url:        String,
        artist:     Artist,
        images:     Map<String,String>,
        tracks:     ArrayList<Track>?,
        tags:       ArrayList<String>?,
        summup:     String?,
        content:    String?
    ) : this(id, name, url, images, artist)
}