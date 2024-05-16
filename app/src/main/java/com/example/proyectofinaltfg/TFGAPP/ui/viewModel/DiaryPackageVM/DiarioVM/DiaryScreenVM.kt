package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiarioVM

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.proyectofinaltfg.TFGAPP.data.Model.NotaModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiaryScreenVM : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _notesData = MutableStateFlow<List<NotaModel>>(emptyList())
    val notesData: StateFlow<List<NotaModel>> = _notesData

    fun fetchNotes() {
        val email = auth.currentUser?.email
        firestore.collection("Notes")
            .whereEqualTo("emailUser", email.toString())
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    Log.e("Firebase", "Error fetching notes: $error")
                    return@addSnapshotListener
                }
                val documents = mutableListOf<NotaModel>()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val myDocument =
                            document.toObject(NotaModel::class.java).copy(idNote = document.id)
                        documents.add(myDocument)
                    }
                }

                val searchNote = if (search.isNotEmpty()) {
                    documents.filter { note ->
                        note.title.contains(search, ignoreCase = true) || note.note.contains(
                            search,
                            ignoreCase = true
                        )
                    }
                } else {
                    documents
                }
                _notesData.value = searchNote
                Log.d("Firebase", "Notes fetched successfully: ${searchNote.size} notes")
            }
    }

    fun fetchNotesDate(date: String) {
        val email = auth.currentUser?.email

        val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)

        val startOfDay = parsedDate?.let { Date(it.time) }
        val endOfDay = parsedDate?.let { Date(it.time + 24 * 60 * 60 * 1000 - 1) }

        Log.d("DiaryScreenVM", "Fetching notes for date: $date ($parsedDate)")

        if (startOfDay != null) {
            if (endOfDay != null) {
                firestore.collection("Notes")
                    .whereEqualTo("emailUser", email.toString())
                    .whereGreaterThanOrEqualTo("fechaCreacion", startOfDay)
                    .whereLessThanOrEqualTo("fechaCreacion", endOfDay)
                    .addSnapshotListener { querySnapshot, error ->
                        if (error != null) {
                            Log.e("Firebase", "Error fetching notes: $error")
                            return@addSnapshotListener
                        }
                        val documents = mutableListOf<NotaModel>()
                        if (querySnapshot != null) {
                            for (document in querySnapshot) {
                                val myDocument = document.toObject(NotaModel::class.java).copy(idNote = document.id)
                                documents.add(myDocument)
                                Log.d("DiaryScreenVM", "Fetched note: $myDocument")
                            }
                        }
                        _notesData.value = documents
                        Log.d("Firebase", "Notes fetched successfully: ${documents.size} notes")
                    }
            }
        }
    }

    var search by mutableStateOf("")
        private set

    fun changeSearch(search: String) {
        this.search = search
    }
}