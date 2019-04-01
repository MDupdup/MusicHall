package com.projetdev.malo.musichall.models

class Disc(
    override val id: Int,
    override val name: String,
    val dateReleased: Int,
    override val thumbnail: String,
    val artists: ArrayList<Artist>,
    val style: List<String>?,
    val formats: List<String>?,
    val country: String,
    val url: String,
    val labels: List<String>,
    val trackList: ArrayList<Track>
) : Result(id, name, thumbnail)