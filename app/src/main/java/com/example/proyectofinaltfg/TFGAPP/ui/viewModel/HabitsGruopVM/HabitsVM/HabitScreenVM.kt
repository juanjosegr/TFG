package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyectofinaltfg.TFGAPP.data.Model.HabitModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
/**
 * ViewModel encargado de gestionar la pantalla de hábitos.
 */
class HabitScreenVM : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = FirebaseFirestore.getInstance()

    private val _habitsData = MutableStateFlow<List<HabitModel>>(emptyList())
    val habitsData: StateFlow<List<HabitModel>> = _habitsData

    // Inicialización para obtener los hábitos al crear la instancia del ViewModel
    init {
        fetchHabits()
    }

    /**
     * Función para obtener todos los hábitos desde Firestore.
     */
    fun fetchHabits() {
        firestore.collection("Habits")
            .orderBy("fechaCreacion", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documents = querySnapshot.documents.map { document ->
                    document.toObject(HabitModel::class.java)!!.copy(idHabit = document.id)
                }
                _habitsData.value = documents
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error fetching notes: $e")
            }
    }
    /**
     * Función para obtener los hábitos creados en una fecha específica.
     * @param date La fecha en formato "yyyy-MM-dd".
     */
    fun fetchHabitsDate(date: String) {
        val email = auth.currentUser?.email

        val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)

        val startOfDay = parsedDate?.let { Date(it.time) }
        val endOfDay = parsedDate?.let { Date(it.time + 24 * 60 * 60 * 1000 - 1) }

        Log.d("HabitScreenVM", "Fetching habits for date: $date ($parsedDate)")

        if (startOfDay != null) {
            if (endOfDay != null) {
                firestore.collection("Habits")
                    .whereEqualTo("emailUser", email.toString())
                    .whereGreaterThanOrEqualTo("fechaCreacion", startOfDay)
                    .whereLessThanOrEqualTo("fechaCreacion", endOfDay)
                    .addSnapshotListener { querySnapshot, error ->
                        if (error != null) {
                            Log.e("Firebase", "Error fetching habits: $error")
                            return@addSnapshotListener
                        }
                        val documents = querySnapshot?.documents?.map { document ->
                            val habit = document.toObject(HabitModel::class.java)!!.copy(idHabit = document.id)
                            Log.d("HabitScreenVM", "Fetched habit: $habit")
                            habit
                        } ?: emptyList()

                        _habitsData.value = documents
                        Log.d("Firebase", "Habits fetched successfully: ${documents.size} habits")
                    }
            }
        }
    }

    /**
     * Función para actualizar el estado de un hábito en Firestore.
     * @param habitId El ID del hábito.
     * @param newStatus El nuevo estado del hábito (por hacer/hecho).
     */
    fun updateHabitStatus(habitId: String, newStatus: String) {
        val habitRef = firestore.collection("Habits").document(habitId)
        habitRef
            .update("hecha_hacer", newStatus)
            .addOnSuccessListener {
                if (newStatus == "hecha") {
                    val currentDate = Date()
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val formattedDate = dateFormat.format(currentDate)
                    val completionData = hashMapOf(
                        "completionDate" to formattedDate
                    )
                    habitRef.collection("CompletionDates")
                        .add(completionData)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Completion date added successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firebase", "Error adding completion date: $e")
                        }
                }
                fetchHabits()
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error updating habit status: $e")
            }
    }
}
