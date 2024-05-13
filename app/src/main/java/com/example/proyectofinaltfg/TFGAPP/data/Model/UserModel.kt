package com.example.proyectofinaltfg.TFGAPP.data.Model

/**
 * Define el modelo de datos para un usuario.
 * Utilizado para gestionar la información de los usuarios en la base de datos.
 */
data class UserModel(
    val userId: String,
    val email: String,
    val pasww: String,
    val userName: String
)