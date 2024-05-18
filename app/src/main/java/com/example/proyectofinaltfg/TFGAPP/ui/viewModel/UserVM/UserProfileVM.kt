package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaltfg.TFGAPP.data.Model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfileVM : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var name by mutableStateOf("")
        private set

    var userName by mutableStateOf("")
        private set

    var eemail by mutableStateOf("")
        private set

    var pasww by mutableStateOf("")
        private set

    var repatPass by mutableStateOf("")
        private set

    var paswwd by mutableStateOf("")
        private set

    var repatPassd by mutableStateOf("")
        private set

    var photoUrl by mutableStateOf("")
        private set

    private var userId: String? = null

    var showUpdateSuccessAlert by mutableStateOf(false)


    var photoUri by mutableStateOf<Uri?>(null)

    fun onPhotoUrlChange(uri: Uri?) {
        photoUri = uri
    }

    fun onNameChange(name: String) {
        this.name = name
    }

    fun onUserNameChange(apelido: String) {
        this.userName = apelido
    }

    fun onPaswwChange(pass: String) {
        this.pasww = pass
    }

    fun onRepeatPassChange(pass: String) {
        this.repatPass = pass
    }

    fun resetUpdateSuccessAlert() {
        showUpdateSuccessAlert = false
    }

    init {
        fetchUser()
    }

    fun fetchUser() {
        //Log.d("UserProfileVM", "Acceso")
        val email = auth.currentUser?.email
        //Log.d("UserProfileVM", "email  $email")
        viewModelScope.launch(Dispatchers.Main) {
            firestore.collection("Users")
                .whereEqualTo("email", email.toString())
                .addSnapshotListener { querySnapshot, error ->
                    //Log.d("UserProfileVM", "Inside addSnapshotListener")
                    if (error != null) {
                        Log.e("Firebase", "Error fetching notes: $error")
                        return@addSnapshotListener
                    }
                    //Log.d("UserProfileVM", "Snapshot received: $querySnapshot")

                    if (querySnapshot != null) {
                        //Log.d("UserProfileVM", "querySnapshot $querySnapshot")

                        for (document in querySnapshot) {
                            //Log.d("UserProfileVM", "querySnapshot interno $querySnapshot")
                            userId = document.id
                            val myDocument = document.toObject(UserModel::class.java)
                            //Log.d("UserProfileVM", "myDocument $myDocument")

                            myDocument.let {
                                //Log.d("UserProfileVM", "User data fetched: $it")
                                name = it.name
                                userName = it.userName
                                eemail = it.email
                                pasww = it.pasww
                                repatPass = it.pasww
                                photoUrl = it.foto

                                photoUri = Uri.parse(it.foto)
                            }
                        }
                    } else {
                        Log.d("UserProfileVM", "No document found for user ID ")
                    }
                }
        }
    }


    fun updateUser(name: String, userName: String, email: String, photoUrl: String) {
        userId?.let { userId ->
            val user = hashMapOf(
                "name" to name,
                "userName" to userName,
                "email" to email,
                "foto" to photoUri.toString()
            )

            viewModelScope.launch(Dispatchers.IO) {
                firestore.collection("Users")
                    .document(userId)
                    .update(user as Map<String, Any>)
                    .addOnSuccessListener {
                        Log.d("updateUser UserProfileVM", "User updated successfully")
                        showUpdateSuccessAlert = true
                    }
                    .addOnFailureListener { e ->
                        Log.e("updateUser UserProfileVM", "Error updating user: $e")
                    }
            }
        }
    }


}
