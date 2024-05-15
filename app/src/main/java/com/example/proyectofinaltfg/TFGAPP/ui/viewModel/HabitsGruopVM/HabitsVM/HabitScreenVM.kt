package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyectofinaltfg.TFGAPP.data.Model.HabitModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HabitScreenVM : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _habitsData = MutableStateFlow<List<HabitModel>>(emptyList())
    val habitsData: StateFlow<List<HabitModel>> = _habitsData

    init {
        fetchNotes()
    }

    fun fetchNotes() {
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

    fun updateHabitStatus(habitId: String, newStatus: String) {
        firestore.collection("Habits").document(habitId)
            .update("hecha_hacer", newStatus)
            .addOnSuccessListener {
                fetchNotes()
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error updating habit status: $e")
            }
    }
}
