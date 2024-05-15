package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsAddVM

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

class AddHabitVM : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = FirebaseFirestore.getInstance()
    var textHabit by mutableStateOf("")
        private set
    var showAlert by mutableStateOf(false)
        private set
    var textError by mutableStateOf("")
        private set
    var casoErrorAcierto by mutableStateOf("")
        private set


    fun changeTextHabit(text: String) {
        this.textHabit = text
        Log.d("Texto", text)
    }

    fun saveNewHabit() {
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            if ( textHabit.isBlank()) {
                Log.d("Error en firabase", "Error con campos en blanco.")
                showAlert = true
                textError = "Campo texto vacío"
                casoErrorAcierto = "Error"
            } else {
                try {
                    val idHabit = UUID.randomUUID().toString()
                    val newHabit = hashMapOf(
                        "Habit" to textHabit,
                        "hecha_hacer" to "por hacer",
                        "idHabit" to idHabit,
                        "fechaCreacion" to Calendar.getInstance().time,
                        "emailUser" to email.toString()
                    )
                    firestore.collection("Habits")
                        .add(newHabit)
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

    private fun resetInfoNote() {
        textHabit = ""
    }

    fun closedShowAlert() {
        showAlert = false
    }
}