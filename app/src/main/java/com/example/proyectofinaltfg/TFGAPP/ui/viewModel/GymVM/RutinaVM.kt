package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.GymVM

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.proyectofinaltfg.TFGAPP.data.Model.Ejercicios
import com.example.proyectofinaltfg.TFGAPP.data.Model.Rutinas
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await


/**
 * ViewModel encargado de gestionar la pantalla de rutinas.
 */
class RutinaVM : ViewModel() {

    private val firestore = Firebase.firestore

    private val _rutinasData = MutableStateFlow<List<Rutinas>>(emptyList())
    val rutinasData: StateFlow<List<Rutinas>> = _rutinasData

    private val _rutinaSeleccionada = MutableStateFlow<Rutinas?>(null)
    val rutinaSeleccionada: StateFlow<Rutinas?> = _rutinaSeleccionada

    init {
        fetchRutinas()
    }

    /**
     * Función para obtener las rutinas desde Firestore.
     */
    fun fetchRutinas() {
        firestore.collection("Rutinas")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val rutinas = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Rutinas::class.java)?.apply {
                        // Cuando se obtiene una rutina, también se obtienen los detalles de los ejercicios
                        fetchDetallesEjercicios(this)
                    }
                }
                _rutinasData.value = rutinas
            }
            .addOnFailureListener { error ->
                Log.e("Firebase", "Error fetching rutinas: $error")
            }
    }

    /**
     * Función para obtener los detalles de los ejercicios dentro de una rutina.
     *
     * @param rutina La rutina de la cual se obtendrán los detalles de los ejercicios.
     */
    private fun fetchDetallesEjercicios(rutina: Rutinas) {
        firestore.collection("Rutinas").document(rutina.nombre)
            .collection("ejercicios")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val ejercicios = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Ejercicios::class.java)
                }
                val rutinaConDetalles = rutina.copy(ejercicios = ejercicios)
                _rutinasData.value = _rutinasData.value.toMutableList().apply {
                    set(indexOf(rutina), rutinaConDetalles)
                }
            }
            .addOnFailureListener { error ->
                Log.e("Firebase", "Error fetching ejercicios: $error")
            }
    }

    /**
     * Función para seleccionar una rutina.
     *
     * @param rutina La rutina que se va a seleccionar.
     */
    fun seleccionarRutina(rutina: Rutinas) {
        _rutinaSeleccionada.value = rutina
    }

    var showAlertScreen by mutableStateOf(false)
        private set
    fun trueShowAlertScreen() {
        showAlertScreen = !showAlertScreen
    }


}
