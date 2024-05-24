package com.example.proyectofinaltfg.TFGAPP.data.Model

import com.google.firebase.firestore.PropertyName

/**
 * Esta clase se utiliza para mapear los datos recibidos desde una API,
 * a objetos Kotlin que puedan ser utilizados en la lógica de la aplicación.
 *
 * @param quote La frase.
 * @param author El autor de la frase.
 * @param category La categoría a la que pertenece la frase.
 */
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