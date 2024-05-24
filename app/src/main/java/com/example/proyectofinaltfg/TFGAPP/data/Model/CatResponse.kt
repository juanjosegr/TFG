package com.example.proyectofinaltfg.TFGAPP.data.Model

import com.google.firebase.firestore.PropertyName
/**
 * Clase de datos que representa la respuesta de una solicitud de información sobre gatos.
 * Esta clase se utiliza para mapear los datos recibidos desde una API,
 * a objetos Kotlin que puedan ser utilizados en la lógica de la aplicación.
 *
 * @param id El identificador único del gato.
 * @param url La URL de la imagen del gato.
 * @param width El ancho de la imagen del gato.
 * @param height La altura de la imagen del gato.
 * @param userEmail El correo electrónico del usuario asociado a firebase.
 */
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
    constructor() : this("", "", 0, 0, "")
}
