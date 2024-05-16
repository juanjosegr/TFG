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

class HabitScreenVM : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = FirebaseFirestore.getInstance()

    private val _habitsData = MutableStateFlow<List<HabitModel>>(emptyList())
    val habitsData: StateFlow<List<HabitModel>> = _habitsData

    init {
        fetchHabits()
    }

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

    fun updateHabitStatus(habitId: String, newStatus: String) {
        firestore.collection("Habits").document(habitId)
            .update("hecha_hacer", newStatus)
            .addOnSuccessListener {
                fetchHabits()
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error updating habit status: $e")
            }
    }
}
