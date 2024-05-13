package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaltfg.TFGAPP.data.Model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginRegisterVM : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordVisible: MutableState<Boolean> = mutableStateOf(false)
        private set
    var showAlert by mutableStateOf(false)
        private set
    var userLoginName by mutableStateOf("")
        private set
    var userName by mutableStateOf("")
        private set
    var textError by mutableStateOf("")
        private set
    var casoErrorAcierto by mutableStateOf("")
        private set

    fun changeEmail(email: String) {
        this.email = email
    }

    fun changePasww(pasww: String) {
        this.password = pasww
    }

    fun chaneUserName(user: String) {
        this.userName = user
    }


    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (email.isBlank() || password.isBlank()) {
                Log.d("Error en firabase", "Error con campos en blanco.")
                showAlert = true
                textError = "Campo email / contraseña vacío"
                casoErrorAcierto = "Error"
            }
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            Log.d("Error en Firebase", "Usuario y/o contraseña incorrectos.")
                            showAlert = true
                            textError = "Campo email / contraseña incorrecto"
                            casoErrorAcierto = "Error"
                        }
                    }
            } catch (e: Exception) {
                Log.d("Error en Jetpack", "Error: ${e.localizedMessage}")
            }
        }
    }

    fun createUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                if (email.isBlank() || userName.isBlank()) {
                    // Campos en blanco, mostrar error
                    Log.d("Error en firabase", "Error con campos en blanco.")
                    showAlert = true
                    textError = "Campo email/usuario vacío"
                    casoErrorAcierto = "Error"
                } else if (!isValidEmail(email)) {
                    // Email no válido, mostrar error
                    Log.d("Error en firabase", "Error con email no valido.")
                    showAlert = true
                    textError = "email incorrecto"
                    casoErrorAcierto = "Error"
                } else {
                    val passwordValidationResult = validatePassword(password)
                    if (!passwordValidationResult.first) {
                        // La contraseña no cumple con los requisitos, mostrar error
                        showAlert = true
                        textError = passwordValidationResult.second
                        casoErrorAcierto = "Error"
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    saveUser(userLoginName, password, userName)
                                    onSuccess()
                                    resetFields()
                                } else {
                                    Log.d("Error en firabase", "Error al crear el user")
                                    showAlert = true
                                    textError = "Error al crear el usuario"
                                    casoErrorAcierto = "Error"
                                }
                            }
                    }
                }

            } catch (e: Exception) {
                Log.d("Error crear user", "Error: ${e.localizedMessage}")
            }
        }
    }

    private fun saveUser(userLoginName: String, pasww: String, userName: String) {
        val uid = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = UserModel(
                email = email.toString(),
                userId = uid.toString(),
                pasww = pasww,
                userName = userName
            )
            // DCS - Añade el usuario a la colección "Users" en la base de datos Firestore
            firestore.collection("Users")
                .add(user)
                .addOnSuccessListener {
                    Log.d(
                        "GUARDAR OK",
                        "Se guardó el usuario correctamente en Firestore"
                    )
                }
                .addOnFailureListener { Log.d("ERROR AL GUARDAR", "ERROR al guardar en Firestore") }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun validatePassword(password: String): Pair<Boolean, String> {
        // Verificar longitud mínima de la contraseña
        if (password.length < 6) {
            return Pair(false, mapErrorToString(InvalidError.LOW_CHARACTERS))
        }

        // Verificar presencia de minúsculas
        if (!password.any { it.isLowerCase() }) {
            return Pair(false, mapErrorToString(InvalidError.NO_LOWERCASE))
        }

        // Verificar presencia de mayúsculas
        if (!password.any { it.isUpperCase() }) {
            return Pair(false, mapErrorToString(InvalidError.NO_UPPERCASE))
        }

        // Verificar presencia de dígitos
        if (!password.any { it.isDigit() }) {
            return Pair(false, mapErrorToString(InvalidError.NO_DIGITS))
        }

        // Verificar presencia de caracteres especiales
        val specialCharacters = "~`!@#$%^&*()-_+=<>?/,.|;:\"'{}[]\\"
        if (!password.any { specialCharacters.contains(it) }) {
            return Pair(false, mapErrorToString(InvalidError.NO_SPECIAL_CHARACTERS))
        }

        // La contraseña pasa todas las validaciones
        return Pair(true, "")
    }


    private fun mapErrorToString(error: InvalidError): String {
        return when (error) {
            InvalidError.LOW_CHARACTERS -> "No hay suficientes caracteres en la contraseña"
            InvalidError.NO_LOWERCASE -> "No hay minuscula"
            InvalidError.NO_UPPERCASE -> "No hay mayuscula"
            InvalidError.NO_DIGITS -> "No hay numeros"
            InvalidError.NO_SPECIAL_CHARACTERS -> "No hay caracteres especiales"
        }
    }

    fun closedShowAlert() {
        showAlert = false
    }

    fun resetFields() {
        email = ""
        password = ""
        userLoginName = ""
    }
}

enum class InvalidError {
    LOW_CHARACTERS,
    NO_LOWERCASE,
    NO_UPPERCASE,
    NO_DIGITS,
    NO_SPECIAL_CHARACTERS,
}