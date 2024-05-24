package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.UserView

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.UserProfileVM

/**
 * Pantalla de perfil de usuario sin opción de edición.
 *
 * @param userProfileVM ViewModel que contiene los datos del perfil de usuario.
 */
@Composable
fun ProfileScreenNoEdit(userProfileVM: UserProfileVM) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            if (userProfileVM.photoUrl.isNotEmpty()) {
                userProfileVM.photoUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(uri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(250.dp)
                            .aspectRatio(1f)
                    )
                }
            } else {
                Text(text = "Cambiar foto perfil", fontSize = 12.sp, color = Color.White, textAlign = TextAlign.Center)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Campos de texto de perfil (no editables)
        ProfileTextField(label = "Nombre", value = userProfileVM.name)
        Spacer(modifier = Modifier.height(8.dp))
        ProfileTextField(label = "Nombre Usuario", value = userProfileVM.userName)
        Spacer(modifier = Modifier.height(8.dp))
        ProfileTextField(label = "Email", value = userProfileVM.eemail)
        Spacer(modifier = Modifier.height(8.dp))
    }
}
/**
 * Componente para mostrar un campo de texto de perfil.
 *
 * @param label Etiqueta del campo.
 * @param value Valor del campo.
 */
@Composable
fun ProfileTextField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(12.dp)
        )
    }
}

/**
 * Pantalla de perfil de usuario con opción de edición.
 *
 * @param userProfileVM ViewModel que contiene los datos del perfil de usuario.
 */
@Composable
fun ProfileScreen(userProfileVM: UserProfileVM) {
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri.value = it
                userProfileVM.onPhotoUrlChange(it)
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable {
                    launcher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (userProfileVM.photoUri == null) {
                Text(text = "Cambiar foto perfil",
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center )
            } else {
                userProfileVM.photoUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(uri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(250.dp)
                            .aspectRatio(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre la imagen y los campos de texto
        TextField(
            value = userProfileVM.name,
            onValueChange = { userProfileVM.onNameChange(it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userProfileVM.userName,
            onValueChange = { userProfileVM.onUserNameChange(it) },
            label = { Text("Nombre Usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userProfileVM.eemail,
            onValueChange = {},
            label = { Text("Email") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userProfileVM.paswwd,
            onValueChange = { userProfileVM.onPaswwChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userProfileVM.repatPassd,
            onValueChange = { userProfileVM.onRepeatPassChange(it) },
            label = { Text("Repite Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}