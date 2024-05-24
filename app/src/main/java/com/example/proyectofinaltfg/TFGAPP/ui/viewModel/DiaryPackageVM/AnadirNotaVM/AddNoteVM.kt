package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.AddNoteVM

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaltfg.TFGAPP.data.Model.NotaModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
/**
 * Clase ViewModel responsable de gestionar añadir nuevas notas.
 */
class AddNoteVM : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    var titleNote by mutableStateOf("")
        private set
    var textNote by mutableStateOf("")
        private set
    var noteColorIndex by mutableStateOf(NotaModel.noteColors[0])
        private set
    var colorNames = listOf("RedOrange", "LightGreen", "Violet", "Blue", "RedPink")
        private set
    var showAlert by mutableStateOf(false)
        private set
    var textError by mutableStateOf("")
        private set
    var casoErrorAcierto by mutableStateOf("")
        private set

    var selectedColorName by mutableStateOf("Elegir color")
        private set
    /**
     * Función para actualizar el título de la nota.
     * @param title El nuevo título de la nota.
     */
    fun changeTitleNote(title: String) {
        this.titleNote = title
        Log.d("Titulo", title)
    }
    /**
     * Función para actualizar el texto de la nota.
     * @param text El nuevo texto de la nota.
     */
    fun changeTextNote(text: String) {
        this.textNote = text
        Log.d("Texto", text)
    }

    /**
     * Función para actualizar el color de la nota.
     * @param color El nuevo color de la nota.
     */
    fun changeColorNote(color: Color) {
        this.noteColorIndex = color
        Log.d("Color", color.toString())
    }
    /**
     * Función para actualizar el color de la nota.
     * @param color El nuevo color de la nota.
     */
    fun changeSelectedColorName(newName: String) {
        selectedColorName = newName
    }

    /**
     * Función para guardar la nueva nota en Firebase Firestore.
     */
    fun saveNewNote() {
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            if (titleNote.isBlank() || textNote.isBlank()) {
                Log.d("Error en firabase", "Error con campos en blanco.")
                showAlert = true
                textError = "Campo título / texto vacío"
                casoErrorAcierto = "Error"
            } else {
                try {
                    val newNote = hashMapOf(
                        "title" to titleNote,
                        "note" to textNote,
                        "noteColorIndex" to noteColorIndex,
                        "fechaCreacion" to Calendar.getInstance().time,
                        "emailUser" to email.toString()
                    )
                    firestore.collection("Notes")
                        .add(newNote)
                        .addOnSuccessListener {
                            Log.d("GUARDAR OK", "Se guardó la nota correctamente en Firestore")
                            resetInfoNote()
                            showAlert = true
                            textError = "Añadido correctamente"
                            casoErrorAcierto = "Correcto"
                        }
                        .addOnFailureListener {
                            Log.d("ERROR AL GUARDAR", "ERROR al guardar en Firestore")
                        }
                } catch (e: Exception) {
                    Log.d("Error de guardado de nota", "Error al guardar ${e.localizedMessage}")
                }
            }
        }
    }
    /**
     * Función para resetear la información de la nota.
     */
    private fun resetInfoNote() {
        titleNote = ""
        textNote = ""
        noteColorIndex = NotaModel.noteColors[0]
        selectedColorName = "Elegir color"
    }
    /**
     * Función para cerrar la alerta.
     */
    fun closedShowAlert() {
        showAlert = false
    }
}