package com.example.proyectofinaltfg.TFGAPP.data.Model

import com.example.proyectofinaltfg.ui.theme.*
import java.util.Date
/**
 * Esta clase se utiliza para almacenar información sobre las notas de los usuarios.
 *
 * @param emailUser El correo electrónico del usuario al que pertenece la nota.
 * @param note El contenido de la nota.
 * @param noteColorIndex Un mapa que almacena el índice de color de la nota.
 * @param title El título de la nota.
 * @param idNote El identificador único de la nota.
 * @param fechaCreacion La fecha de creación de la nota.
 */
data class NotaModel(
    val emailUser: String = "",
    val note: String = "",
    val noteColorIndex: HashMap<String, Any> = hashMapOf(),
    val title: String = "",
    val idNote: String = "",
    val fechaCreacion: Date = Date()

    ){
    companion object{
        val noteColors = listOf(RedOrange,LightGreen, Violet, BlueOcean, RedPink)
    }
}