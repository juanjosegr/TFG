package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.ApiVM

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaltfg.TFGAPP.data.Model.CatResponse
import com.example.proyectofinaltfg.TFGAPP.data.Model.PharsesResponse
import com.example.proyectofinaltfg.TFGAPP.data.Retrofit.RetrofitServiceFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ApiVM : ViewModel() {

    private val _catList = mutableStateOf<List<CatResponse>>(emptyList())
    val catList: State<List<CatResponse>> = _catList

    private val _PharsesList = mutableStateOf<List<PharsesResponse>>(emptyList())
    val pharsesList: State<List<PharsesResponse>> = _PharsesList

    private val catService = RetrofitServiceFactory.makeCatService()
    private val phrarsesService = RetrofitServiceFactory.makePharsesService()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()


    init {
        fetchCats()
        fetchPharses()
    }

    private fun fetchCats() {
        viewModelScope.launch {
            try {
                val cats = catService.listOfCats()
                _catList.value = cats
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchPharses() {
        viewModelScope.launch {
            try {
                val apiKey = "w7fTGlcVa/uK13uofKgliQ==XQwMR6RvxA3rQogR"
                val quotes = phrarsesService.listOfQuotes(apiKey)
                _PharsesList.value = quotes
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun reloadCats() {
        fetchCats()
        fetchPharses()
    }

    fun saveCatToFirestore(cat: CatResponse, phrase: PharsesResponse) {
        val userEmail = auth.currentUser?.email
        viewModelScope.launch {
            try {
                val dataMap = mapOf(
                    "id" to cat.id,
                    "url" to cat.url,
                    "width" to cat.width,
                    "height" to cat.height,
                    "userEmail" to userEmail,
                    "quote" to phrase.quote,
                    "author" to phrase.author
                )
                firestore.collection("liked_cats").add(dataMap).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getLikedCatsAndPhrases(): Flow<List<Pair<CatResponse, PharsesResponse>>> = flow {
        val userEmail = auth.currentUser?.email
        if (userEmail != null) {
            val querySnapshot = firestore.collection("liked_cats")
                .whereEqualTo("userEmail", userEmail)
                .get()
                .await()

            val catAndPhraseList = mutableListOf<Pair<CatResponse, PharsesResponse>>()
            for (document in querySnapshot.documents) {
                val cat = document.toObject<CatResponse>()
                val quote = document.getString("quote") ?: ""
                val author = document.getString("author") ?: ""

                val phrase = PharsesResponse(quote = quote, author = author, category = "")
                if (cat != null) {
                    catAndPhraseList.add(Pair(cat, phrase))
                }
            }

            emit(catAndPhraseList)
        }
    }.flowOn(Dispatchers.IO)


    fun deleteCatFromFirestore(cat: CatResponse) {
        val userEmail = auth.currentUser?.email
        viewModelScope.launch {
            try {
                val querySnapshot = firestore.collection("liked_cats")
                    .whereEqualTo("userEmail", userEmail)
                    .whereEqualTo("id", cat.id)
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    document.reference.delete().await()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}