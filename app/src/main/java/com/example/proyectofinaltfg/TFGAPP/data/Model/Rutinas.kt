package com.example.proyectofinaltfg.TFGAPP.data.Model
/**
 * Clase de datos que representa una rutina de ejercicios en el gimnasio.
 *
 * @param nombre El nombre de la rutina.
 * @param img La URL de la imagen representativa de la rutina (opcional).
 * @param ejercicios La lista de ejercicios que componen la rutina.
 */
data class Rutinas(
    val nombre: String = "",
    val img: String = "",
    val ejercicios: List<Ejercicios> = emptyList()
)
/**
 * Clase de datos que representa un ejercicio dentro de una rutina en el gimnasio.
 *
 * @param nombre El nombre del ejercicio.
 * @param descripcion La descripción del ejercicio.
 * @param repeticiones Las repeticiones recomendadas para el ejercicio.
 */
data class Ejercicios(
    val nombre: String = "",
    val descripcion: String = "",
    val repeticiones: String = ""
)
