package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM

import android.content.Context
import android.content.SharedPreferences
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
/**
 * ViewModel para manejar la lógica de inicio de sesión y registro de usuarios.
 */
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
    /**
     * Cambia el valor del correo electrónico.
     * @param email El nuevo valor del correo electrónico.
     */
    fun changeEmail(email: String) {
        this.email = email
    }
    /**
     * Cambia el valor de la contraseña.
     * @param password El nuevo valor de la contraseña.
     */
    fun changePasww(pasww: String) {
        this.password = pasww
    }
    /**
     * Cambia el valor del nombre de usuario.
     * @param userName El nuevo valor del nombre de usuario.
     */
    fun chaneUserName(user: String) {
        this.userName = user
    }

    /**
     * Inicia sesión con el correo electrónico y la contraseña proporcionados.
     * @param onSuccess Función de devolución de llamada a ejecutar en caso de éxito.
     */
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
    /**
     * Crea un nuevo usuario con los datos proporcionados.
     * @param onSuccess Función de devolución de llamada a ejecutar en caso de éxito.
     */
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
    /**
     * Guarda la información del usuario en Firestore.
     * @param userLoginName Nombre de usuario para el login.
     * @param password Contraseña del usuario.
     * @param userName Nombre de usuario.
     */
    private fun saveUser(userLoginName: String, pasww: String, userName: String) {
        val uid = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = UserModel(
                email = email.toString(),
                userId = uid.toString(),
                pasww = pasww,
                userName = userName,
                name = "",
                foto = ""
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
    /**
     * Verifica si el correo electrónico tiene un formato válido.
     * @param email Correo electrónico a validar.
     * @return true si el correo electrónico es válido, false en caso contrario.
     */
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
    /**
     * Valida que la contraseña cumpla con los criterios definidos.
     * @param password Contraseña a validar.
     * @return Pair con un Boolean indicando si la contraseña es válida y un String con el mensaje de error si no lo es.
     */
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

    /**
     * Mapea un error de validación a su correspondiente mensaje de error.
     * @param error Error de validación.
     * @return Mensaje de error correspondiente.
     */
    private fun mapErrorToString(error: InvalidError): String {
        return when (error) {
            InvalidError.LOW_CHARACTERS -> "No hay suficientes caracteres en la contraseña"
            InvalidError.NO_LOWERCASE -> "No hay minuscula"
            InvalidError.NO_UPPERCASE -> "No hay mayuscula"
            InvalidError.NO_DIGITS -> "No hay numeros"
            InvalidError.NO_SPECIAL_CHARACTERS -> "No hay caracteres especiales"
        }
    }
    /**
     * Oculta la alerta de error.
     */
    fun closedShowAlert() {
        showAlert = false
    }
    /**
     * Restablece los campos de entrada.
     */
    fun resetFields() {
        email = ""
        password = ""
        userLoginName = ""
    }

    var recordarUsuario by mutableStateOf(false)

    init {
        recordarUsuario = PreferenceManager.getRememberUser()
        if (recordarUsuario) {
            email = PreferenceManager.getEmail()
            password = PreferenceManager.getPassword()
        }
    }
    /**
     * Guarda las preferencias de usuario.
     */
    fun guardarPreferencias() {
        PreferenceManager.setRememberUser(recordarUsuario)
        if (recordarUsuario) {
            PreferenceManager.setEmail(email)
            PreferenceManager.setPassword(password)
        } else {
            PreferenceManager.setEmail("")
            PreferenceManager.setPassword("")
        }
    }
}
/**
 * Enumeración de errores de validación de contraseñas.
 */
enum class InvalidError {
    LOW_CHARACTERS,
    NO_LOWERCASE,
    NO_UPPERCASE,
    NO_DIGITS,
    NO_SPECIAL_CHARACTERS,
}

/**
 * Object para gestionar las preferencias del usuario.
 */
object PreferenceManager {
    private const val PREF_NAME = "login_prefs"
    private lateinit var sharedPreferences: SharedPreferences
    /**
     * Inicializa las preferencias del usuario.
     * @param context Contexto de la aplicación.
     */
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    /**
     * Establece si se debe recordar al usuario.
     * @param value Valor booleano indicando si se debe recordar al usuario.
     */
    fun setRememberUser(value: Boolean) {
        sharedPreferences.edit().putBoolean("remember_user", value).apply()
    }
    /**
     * Obtiene el valor indicando si se debe recordar al usuario.
     * @return true si se debe recordar al usuario, false en caso contrario.
     */
    fun getRememberUser(): Boolean {
        return sharedPreferences.getBoolean("remember_user", false)
    }
    /**
     * Establece el correo electrónico del usuario.
     * @param email Correo electrónico del usuario.
     */
    fun setEmail(email: String) {
        sharedPreferences.edit().putString("email", email).apply()
    }
    /**
     * Obtiene el correo electrónico del usuario.
     * @return Correo electrónico del usuario.
     */
    fun getEmail(): String {
        return sharedPreferences.getString("email", "") ?: ""
    }
    /**
     * Establece la contraseña del usuario.
     * @param password Contraseña del usuario.
     */
    fun setPassword(password: String) {
        sharedPreferences.edit().putString("password", password).apply()
    }
    /**
     * Obtiene la contraseña del usuario.
     * @return Contraseña del usuario.
     */
    fun getPassword(): String {
        return sharedPreferences.getString("password", "") ?: ""
    }
}