package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateHabitVM : ViewModel() {

    private val firestore = Firebase.firestore

    var textHabit by mutableStateOf("")
        private set

    var idHabit by mutableStateOf("")
        private set
    var showAlert by mutableStateOf(false)
        private set
    var textError by mutableStateOf("")
        private set
    var casoErrorAcierto by mutableStateOf("")
        private set

    var hechaHacer by mutableStateOf("")

    fun changeTextHabit(text: String) {
        this.textHabit = text
        Log.d("Texto", text)
    }

    fun allDateObtains(
        text: String,
        idHabit: String,
        hechaHacer: String
    ) {
        this.textHabit = text
        this.idHabit = idHabit
        this.hechaHacer = hechaHacer
        Log.d("Obtección de datos",  text  + idHabit + hechaHacer)
        updateState(idHabit)
    }

    fun updateHabitVM(idHabit: String) {
        Log.d("UpdateNoteComponent", "ID de la nota antes de la actualización: $idHabit")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (idHabit.isNotEmpty()) {
                    val editHabit = hashMapOf(
                        "Habit" to textHabit,
                        "hecha_hacer" to hechaHacer
                    )
                    // DCS - Utiliza la instancia de Firestore para actualizar la info de una nota por su id
                    firestore.collection("Habits").document(idHabit)
                        .update(editHabit as Map<String, Any>)
                        .addOnSuccessListener {
                            Log.d(
                                "ACTUALIZAR OK",
                                "Se actualizó la nota correctamente en Firestore"
                            )
                            showAlert = true
                            textError = "Actualizado correctamente"
                            casoErrorAcierto = "Correcto"
                        }
                        .addOnFailureListener {
                            Log.d(
                                "ERROR AL ACTUALIZAR",
                                "ERROR al actualizar una nota en Firestore"
                            )
                            showAlert = true
                            textError = "Error al actualizar"
                            casoErrorAcierto = "Error"
                        }
                    // DCS - Si se guarda con éxito limpiamos las variables

                } else {
                    Log.d("Error editar nota", "ID no encontrada")
                }

            } catch (e: Exception) {
                Log.d("ERROR EDITAR NOTA", "Error al editar ${e.localizedMessage} ")
            }
        }
    }

    fun updateState(idHabit: String) {
        Log.d("UpdateNoteComponent", "ID de la nota antes de la actualización: $idHabit")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (idHabit.isNotEmpty()) {
                    val editHabit = hashMapOf(
                        "Habit" to textHabit,
                        "hecha_hacer" to hechaHacer
                    )
                    // Utiliza la instancia de Firestore para actualizar la información de una nota por su id
                    firestore.collection("Habits").document(idHabit)
                        .update(editHabit as Map<String, Any>)
                        .addOnSuccessListener {
                            Log.d(
                                "ACTUALIZAR OK",
                                "Se actualizó la nota correctamente en Firestore"
                            )
                        }
                        .addOnFailureListener {
                            Log.d(
                                "ERROR AL ACTUALIZAR",
                                "ERROR al actualizar una nota en Firestore"
                            )
                        }
                } else {
                    Log.d("Error editar nota", "ID no encontrada")
                }
            } catch (e: Exception) {
                Log.d("ERROR EDITAR NOTA", "Error al editar ${e.localizedMessage} ")
            }
        }
    }

    fun deleteHabitVM(idHabit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // DCS - Utiliza la instancia de Firestore para eliminar una nota por su id
                firestore.collection("Habits").document(idHabit)
                    .delete()
                    .addOnSuccessListener {
                        showAlert = true
                        textError = "Borrado Correctamente"
                        casoErrorAcierto = "Borrado"
                        Log.d("ELIMINAR OK", "Se eliminó la nota correctamente en Firestore")
                    }
                    .addOnFailureListener {
                        Log.d("ERROR AL ELIMINAR", "ERROR al eliminar una nota en Firestore")
                    }
            } catch (e: Exception) {
                Log.d("ERROR BORRAR NOTA", "Error al eliminar ${e.localizedMessage} ")
            }
        }
    }

    fun closedShowAlert() {
        showAlert = false
    }
}