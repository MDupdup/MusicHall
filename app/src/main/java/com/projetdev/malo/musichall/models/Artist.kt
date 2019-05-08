package com.projetdev.malo.musichall.models

class Artist(
    val name: String
) {
    constructor(
        name: String,
        images: Map<String, String>?
    ) :this(name)

    constructor(
        id: String,
        name: String,
        url: String?,
        playCount: Long = 0,
        images: Map<String, String>?
    ) : this(name)

    constructor(
        id:         String?,
        name:       String,
        url:        String?,
        playCount:  Long = 0,
        images:     Map<String,String>?,
        isOnTour:   Boolean = false,
        similar:    ArrayList<Artist>?,
        summup:     String?,
        content:    String?
    ) : this(name)
}
