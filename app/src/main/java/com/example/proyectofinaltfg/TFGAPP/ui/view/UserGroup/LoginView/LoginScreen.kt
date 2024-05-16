package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.LoginView

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.LoginRegisterVM
import com.example.proyectofinaltfg.arriba.Arriba

@Composable
fun LoginScreen(navController: NavController, loginScreenVM: LoginRegisterVM) {
    var showExitDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler(enabled = true) {
        if (!showExitDialog) {
            showExitDialog = true
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = {

            },
            title = {
                Text(text = "¿Cerrar la aplicación?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        ActivityCompat.finishAffinity(context as Activity)
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Arriba()
            GrupoLoginNuevo(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.5f),
                loginScreenVM = loginScreenVM,
                onBtnLogin = { loginScreenVM.login { navController.navigate(Routes.principalMenuScreen.routes) } },
                onRegistedTap = {
                    loginScreenVM.resetFields()
                    navController.navigate(Routes.registerScreen.routes)
                },
                passwordVisible = loginScreenVM.passwordVisible
            )
            LlamadaShowAler(loginScreenVM, loginScreenVM.textError, loginScreenVM.casoErrorAcierto)
        }
    }
}