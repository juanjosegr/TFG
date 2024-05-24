package com.example.proyectofinaltfg.TFGAPP.data.Model

/**
 * Esta clase se utiliza para almacenar información sobre los usuarios.
 *
 * @param userId El identificador único del usuario.
 * @param email El correo electrónico del usuario.
 * @param pasww La contraseña del usuario.
 * @param userName El nick de usuario del usuario.
 * @param name El nombre del usuario.
 * @param foto La URL de la foto de perfil del usuario.
 */
data class UserModel(
    val userId: String,
    val email: String,
    val pasww: String,
    val userName: String,
    val name: String,
    val foto: String
){
    constructor() : this("", "", "", "", "", "")
}