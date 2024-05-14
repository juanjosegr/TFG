package com.example.proyectofinaltfg.TFGAPP.data.Model

import java.util.Date

data class HabitModel(
    val emailUser: String = "",
    val habit: String = "",
    val idNote: String = "",
    val hecha_hacer: String = "",
    val fechaCreacion: Date = Date()
)