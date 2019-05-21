package com.projetdev.malo.musichall.models

class Artist(
    override val name: String
)  : Item("", name, "", null) {

    override val id: String = ""
    override val images: Map<String, String>? = null
    override val url: String = ""
    val playCount: Long = 0
    val isOnTour: Boolean = false;
    val similar: ArrayList<Artist>? = null
    val summup: String = ""
    val content: String = ""


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
        summup:     String,
        content:    String?
    ) : this(name)
}

