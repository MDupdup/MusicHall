package com.projetdev.malo.musichall.models

class Disc(
    val id: Int,
    val name: String,
    val dateReleased: Int,
    val thumbnail: String,
    val artists: ArrayList<Artist>,
    val style: List<String>?,
    val formats: List<String>?,
    val country: String,
    val url: String,
    val labels: List<String>,
    val trackList: ArrayList<Track>
)