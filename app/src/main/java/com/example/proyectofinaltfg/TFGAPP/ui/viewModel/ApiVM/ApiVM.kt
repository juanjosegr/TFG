package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.ApiVM

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaltfg.TFGAPP.data.Model.CatResponse
import com.example.proyectofinaltfg.TFGAPP.data.Model.PharsesResponse
import com.example.proyectofinaltfg.TFGAPP.data.Retrofit.RetrofitServiceFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
/**
 * Clase ViewModel encargada de gestionar las llamadas a la API relacionadas con gatos y frases, así como las interacciones con Firestore
 * para guardar, recuperar y eliminar gatos y frases marcados como favoritos.
 */
class ApiVM : ViewModel() {

    // Estado mutable para almacenar la lista de respuestas de gatos
    private val _catList = mutableStateOf<List<CatResponse>>(emptyList())
    val catList: State<List<CatResponse>> = _catList

    // Estado mutable para almacenar la lista de respuestas de frases
    private val _PharsesList = mutableStateOf<List<PharsesResponse>>(emptyList())
    val pharsesList: State<List<PharsesResponse>> = _PharsesList

    // Servicio Retrofit para obtener datos de gatos
    private val catService = RetrofitServiceFactory.makeCatService()
    // Servicio Retrofit para obtener datos de frases
    private val phrarsesService = RetrofitServiceFactory.makePharsesService()
    // Instancia de Firestore para interactuar con la base de datos
    private val firestore = FirebaseFirestore.getInstance()
    // Instancia de autenticación de Firebase para autenticación de usuarios
    private val auth = FirebaseAuth.getInstance()

    var showAlertScreen by mutableStateOf(false)
        private set

    var showAlertLike by mutableStateOf(false)
        private set

    /** Método para mostrar u ocultar la alerta de pantalla
     */
    fun trueShowAlertScreen() {
        showAlertScreen = !showAlertScreen
    }

    /**
     *  Método para mostrar u ocultar la alerta de "me gusta"
     */
    fun LikeShowAlertScreen() {
        showAlertLike = !showAlertLike
    }

    var deleteConfirmationDialog by mutableStateOf(false)
        private set
    /**
     * Método para mostrar u ocultar el diálogo de confirmación de eliminación
     */
    fun showDeleteConfirmationDialog() {
        deleteConfirmationDialog = !deleteConfirmationDialog
    }


    init {
        fetchCats()
        fetchPharses()
    }

    /**
     * Método privado para obtener los datos de gatos de la API
     */
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

    /**
      * Método privado para obtener los datos de frases de la API
     */
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

    /**
     *  Método para recargar los datos de gatos y frases
     */
    fun reloadCats() {
        fetchCats()
        fetchPharses()
    }

    /**
     * Método para guardar un gato marcado como favorito en Firestore
     */
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

    /**
     * Método para obtener los gatos y frases marcados como favoritos
     *
     */
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

    /**
     * Método para eliminar un gato de Firestore
     *
     */
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