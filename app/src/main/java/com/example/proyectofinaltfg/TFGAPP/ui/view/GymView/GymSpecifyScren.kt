package com.example.proyectofinaltfg.TFGAPP.ui.view.GymView

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.data.Model.Ejercicios
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.GymVM.RutinaVM
import com.example.proyectofinaltfg.arribagym.ArribaGym
import com.example.proyectofinaltfg.menuabajovariant2.MenuAbajoVariant2

/**
 * Pantalla que muestra los detalles de una rutina seleccionada, incluyendo los detalles de los ejercicios asociados.
 *
 * Esta pantalla muestra los detalles de una rutina seleccionada, incluyendo los detalles de cada ejercicio
 * asociado a la rutina.
 *
 * @param navController El NavController para la navegación entre pantallas.
 * @param rutinaVM El ViewModel que proporciona los datos de las rutinas.
 */
@Composable
fun GymSpecifyScren(
    navController: NavController,
    rutinaVM: RutinaVM
) {
    BackHandler {
        navController.navigate(Routes.gymScreen.routes)
    }
    val rutinaSeleccionada = rutinaVM.rutinaSeleccionada.collectAsState().value
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Box { ArribaGym() }
            rutinaSeleccionada?.let { rutina ->
                rutina.ejercicios.forEach { ejercicio ->
                    EjercicioDetalleComponent(ejercicio = ejercicio)
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoVariant2(
                onGoHome2 = { navController.navigate(Routes.gymScreen.routes) },
                onUserGo2 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}

/**
 * Componente composable que muestra los detalles de un ejercicio.
 *
 * @param ejercicio El ejercicio del cual se mostrarán los detalles.
 */
@Composable
fun EjercicioDetalleComponent(ejercicio: Ejercicios) {
    val viewModel: RutinaVM = viewModel()
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                viewModel.trueShowAlertScreen()
            }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)

        ) {
            Text(text = ejercicio.nombre, style = MaterialTheme.typography.h6)
            Text(text = "Nombre: ${ejercicio.nombre}")
            Text(text = "Descripción: ${ejercicio.descripcion}")
            Text(text = "Repeticiones: ${ejercicio.repeticiones}")
        }
    }
    if (viewModel.showAlertScreen) {
        AlertDialog(
            onDismissRequest = {
                viewModel.trueShowAlertScreen()
            },
            text = {
                Box{
                    YouTubeVideoPlayer(ejercicio.video)
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.trueShowAlertScreen() }) {
                    Text("Cerrar")
                }
            },modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubeVideoPlayer(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    )
}