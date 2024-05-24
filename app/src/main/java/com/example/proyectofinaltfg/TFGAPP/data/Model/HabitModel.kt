package com.example.proyectofinaltfg.TFGAPP.data.Model

import java.util.Date
import java.util.UUID
/**
 * Esta clase se utiliza para almacenar información sobre los hábitos de los usuarios.
 *
 * @param emailUser El correo electrónico del usuario al que pertenece el hábito.
 * @param Habit El nombre del hábito.
 * @param idHabit El identificador único del hábito, generado automáticamente.
 * @param hecha_hacer Un indicador que especifica si el hábito ha sido realizado o está pendiente de realizar.
 * @param fechaCreacion La fecha de creación del hábito.
 */
data class HabitModel(
    val emailUser: String = "",
    val Habit: String = "",
    val idHabit: String = UUID.randomUUID().toString(),
    val hecha_hacer: String = "",
    val fechaCreacion: Date = Date()
)