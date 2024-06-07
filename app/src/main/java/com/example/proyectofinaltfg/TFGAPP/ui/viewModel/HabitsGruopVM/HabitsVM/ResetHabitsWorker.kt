package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ResetHabitsWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val firestore = FirebaseFirestore.getInstance()

        return try {
            val habitsCollection = firestore.collection("Habits")
            val habitsSnapshot = habitsCollection.get().await()

            for (document in habitsSnapshot.documents) {
                document.reference.update("hecha_hacer", "por hacer").await()
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}