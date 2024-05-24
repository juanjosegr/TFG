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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/**
 * ViewModel para manejar la lógica del perfil del usuario.
 */
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

    /**
     * Actualiza el URI de la foto de perfil.
     * @param uri Nuevo URI de la foto de perfil.
     */
    fun onPhotoUrlChange(uri: Uri?) {
        photoUri = uri
    }

    /**
     * Actualiza el nombre del usuario.
     * @param name Nuevo nombre del usuario.
     */
    fun onNameChange(name: String) {
        this.name = name
    }

    /**
     * Actualiza el nombre de usuario.
     * @param userName Nuevo nombre de usuario.
     */
    fun onUserNameChange(apelido: String) {
        this.userName = apelido
    }

    /**
     * Actualiza la contraseña del usuario.
     * @param pass Nueva contraseña del usuario.
     */
    fun onPaswwChange(pass: String) {
        this.pasww = pass
    }


    /**
     * Actualiza la repetición de la contraseña del usuario.
     * @param pass Nueva repetición de la contraseña del usuario.
     */
    fun onRepeatPassChange(pass: String) {
        this.repatPass = pass
    }

    /**
     * Reinicia la alerta de éxito al actualizar.
     */
    fun resetUpdateSuccessAlert() {
        showUpdateSuccessAlert = false
    }


    /**
     * Obtiene los datos del usuario actual desde Firestore.
     */
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

    /**
     * Actualiza los datos del usuario en Firestore.
     * @param name Nuevo nombre del usuario.
     * @param userName Nuevo nombre de usuario.
     * @param email Nuevo correo electrónico del usuario.
     */
    fun updateUser(name: String, userName: String, email: String) {
        userId?.let { userId ->
            val user = hashMapOf(
                "name" to name,
                "userName" to userName,
                "email" to email
            )
            viewModelScope.launch(Dispatchers.IO) {
                firestore.collection("Users")
                    .document(userId)
                    .update(user as Map<String, Any>)
                    .addOnSuccessListener {
                        Log.d("updateUser UserProfileVM", "User updated successfully")
                        photoUri?.let { uri ->
                            uploadPhotoToStorage(uri)
                        }
                        showUpdateSuccessAlert = true

                    }
                    .addOnFailureListener { e ->
                        Log.e("updateUser UserProfileVM", "Error updating user: $e")
                    }
            }
        }
    }

    /**
     * Sube la foto de perfil del usuario a Firebase Storage.
     * @param uri URI de la foto de perfil.
     */
    private fun uploadPhotoToStorage(uri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val photoRef = storageRef.child("profile_photos/${auth.currentUser?.uid}")

        val uploadTask = photoRef.putFile(uri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                photoUrl = downloadUri.toString()
                Log.d("uploadPhotoToStorage", "Photo URL updated: $photoUrl")
                photoUri = downloadUri
                onPhotoUrlChange(photoUri)
            }.addOnFailureListener { e ->
                Log.e("uploadPhotoToStorage", "Error getting download URL: $e")
            }
        }.addOnFailureListener { e ->
            Log.e("uploadPhotoToStorage", "Error uploading photo: $e")
        }
    }

}