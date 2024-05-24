package com.example.proyectofinaltfg.TFGAPP.ui.view.PrincipalView

import android.app.Activity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.agenadatop.AgenadaTop

/**
 * Pantalla principal de la aplicación que muestra el menú principal y permite navegar a diferentes secciones.
 *
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 */
@Composable
fun PrincipalMenuScreen(
    navController: NavController
) {
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
                        finishAffinity(context as Activity)
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
            AgenadaTop()
            Agrupadoss(
                onGroupDiary = { navController.navigate(Routes.diarioScreen.routes) },
                onGroupHabits = { navController.navigate(Routes.habtisScreen.routes) },
                onGroupCalendary = { navController.navigate(Routes.calendaryScreen.routes) },
                onGroupTraining = { navController.navigate(Routes.gymScreen.routes) },
                onGroupAleatory = { navController.navigate(Routes.apiScreen.routes) }
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoaVariant2(
                onUserGo2 = {
                    navController.navigate(Routes.userScren.routes)
                }
            )
        }
    }
}
