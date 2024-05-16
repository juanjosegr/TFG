package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.ApiVM

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaltfg.TFGAPP.data.Model.CatResponse
import com.example.proyectofinaltfg.TFGAPP.data.Retrofit.RetrofitServiceFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ApiVM : ViewModel() {

    private val _catList = mutableStateOf<List<CatResponse>>(emptyList())
    val catList: State<List<CatResponse>> = _catList

    private val service = RetrofitServiceFactory.makeRetrofitService()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()


    init {
        fetchCats()
    }

    private fun fetchCats() {
        viewModelScope.launch {
            try {
                val cats = service.listOfCats()
                _catList.value = cats
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun reloadCats() {
        fetchCats()
    }

    // Función para guardar la imagen en Firestore
    fun saveCatToFirestore(cat: CatResponse) {
        val userEmail = auth.currentUser?.email
        viewModelScope.launch {
            try {
                val catMap = mapOf(
                    "id" to cat.id,
                    "url" to cat.url,
                    "width" to cat.width,
                    "height" to cat.height,
                    "userEmail" to userEmail
                )
                firestore.collection("liked_cats").add(catMap).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}