package com.example.proyectofinaltfg.TFGAPP.data.Model

import com.google.firebase.firestore.PropertyName

data class CatResponse(
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String = "",

    @get:PropertyName("url")
    @set:PropertyName("url")
    var url: String = "",

    @get:PropertyName("width")
    @set:PropertyName("width")
    var width: Int = 0,

    @get:PropertyName("height")
    @set:PropertyName("height")
    var height: Int = 0,

    @get:PropertyName("userEmail")
    @set:PropertyName("userEmail")
    var userEmail: String = ""
) {
    // Constructor sin argumentos requerido por Firebase
    constructor() : this("", "", 0, 0, "")
}
