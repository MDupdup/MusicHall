package com.projetdev.malo.musichall.models

import com.projetdev.malo.musichall.models.Track

class Album(
    val id:         String,
    val title:      String,
    val url:        String,
    val artist:     Artist,
    val images:     Map<String,String>,
    val tracks:     ArrayList<Track>?,
    val tags:       Map<String,String>?,
    val summup:     String?,
    val content:    String?
)