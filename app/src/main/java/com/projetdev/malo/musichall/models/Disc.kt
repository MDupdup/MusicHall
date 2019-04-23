package com.projetdev.malo.musichall.models

class Disc(
    override val id: Int, override val name: String, dateReleased: Int, override val thumbnail: String, artists: ArrayList<Artist>, style: List<String>?
) : Result(id, name, thumbnail, "", ""+dateReleased, style) {
    constructor(
        id: Int,
        name: String,
        dateReleased: Int,
        thumbnail: String,
        artists: ArrayList<Artist>,
        style: List<String>?,
        formats: List<String>?,
        country: String,
        url: String,
        labels: List<String>,
        trackList: ArrayList<Track>
        ) : this(id, name, dateReleased, thumbnail, artists, style)
}