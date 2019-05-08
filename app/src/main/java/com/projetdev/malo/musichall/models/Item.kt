package com.projetdev.malo.musichall.models

abstract class Item(
    open val id:    String,
    open val name:  String,
    open val url:   String,
    open val images:Map<String,String>?
)