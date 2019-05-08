package com.projetdev.malo.musichall.models

class Artist(
    val id:         String              = "",
    var name:       String,
    val url:        String              = "",
    val playCount:  Long                = 0,
    val images:     Map<String,String>? = null,
    val isOnTour:   Boolean             = false,
    val similar:    ArrayList<Artist>?  = null,
    val summup:     String              = "",
    val content:    String              = ""
) {
    constructor(name: String) : this(name)
}
