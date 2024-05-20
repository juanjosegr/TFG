package com.example.proyectofinaltfg.TFGAPP.data.Model

import com.google.firebase.firestore.PropertyName


data class PharsesResponse(
    @get:PropertyName("quote")
    @set:PropertyName("quote")
    var quote: String = "",

    @get:PropertyName("author")
    @set:PropertyName("author")
    var author: String = "",

    @get:PropertyName("category")
    @set:PropertyName("category")
    var category: String = ""
) {
    constructor() : this("", "", "")
}