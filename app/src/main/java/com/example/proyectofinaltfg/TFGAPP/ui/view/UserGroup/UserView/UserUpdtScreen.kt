package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.UserView

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.UserProfileVM
import com.example.proyectofinaltfg.menuabajovariant3.MenuAbajoVariant3
import com.example.proyectofinaltfg.usertop.UserTop

/**
 * Pantalla de actualización de perfil de usuario.
 *
 * @param navController Controlador de navegación de Jetpack Compose.
 * @param userProfileVM ViewModel que contiene los datos del perfil de usuario.
 */
@Composable
fun UserUpdtScreen(
    navController: NavController,
    userProfileVM: UserProfileVM,
) {
    BackHandler {
        navController.navigate(Routes.userScren.routes)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Box {
                UserTop()
            }
            ProfileScreen(userProfileVM)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoVariant3(
                onHomeGo3 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onBtnDesconectar = { },
                onactuBtn = {
                    userProfileVM.updateUser(
                        userProfileVM.name,
                        userProfileVM.userName,
                        userProfileVM.eemail,
                    )
                }
            )
        }
    }

    // Mostrar el diálogo de éxito de actualización
    if (userProfileVM.showUpdateSuccessAlert) {
        AlertDialog(
            onDismissRequest = {
                userProfileVM.resetUpdateSuccessAlert()
            },
            title = { Text(text = "Usuario Actualizado") },
            text = { Text(text = "Los datos del usuario se han actualizado correctamente.") },
            confirmButton = {
                Button(
                    onClick = {
                        userProfileVM.resetUpdateSuccessAlert()
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}


