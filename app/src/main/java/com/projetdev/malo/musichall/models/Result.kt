package com.projetdev.malo.musichall.models

abstract class Result(
    open val id: Int,
    open val name: String,
    open val thumbnail: String,
    open val description: String,
    open val year: String,
    open val style: List<String>?
)