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

    private val _HabitsData = MutableStateFlow<List<HabitModel>>(emptyList())
    val habitsData: StateFlow<List<HabitModel>> = _HabitsData

    fun fetchNotes() {
        firestore.collection("Habits")
            .orderBy("fechaCreacion", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documents = mutableListOf<HabitModel>()
                for (document in querySnapshot) {
                    val myDocument =
                        document.toObject(HabitModel::class.java).copy(idNote = document.id)
                    documents.add(myDocument)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error fetching notes: $e")
            }
    }

}