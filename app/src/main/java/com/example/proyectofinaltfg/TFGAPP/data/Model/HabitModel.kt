package com.example.proyectofinaltfg.TFGAPP.data.Model

import java.util.Date
import java.util.UUID

data class HabitModel(
    val emailUser: String = "",
    val Habit: String = "",
    val idHabit: String = UUID.randomUUID().toString(),
    val hecha_hacer: String = "",
    val fechaCreacion: Date = Date()
)