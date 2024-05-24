package com.example.proyectofinaltfg.TFGAPP.ui.view.GymView

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.data.Model.Rutinas
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.GymVM.RutinaVM
import com.example.proyectofinaltfg.arribagym.ArribaGym
import com.example.proyectofinaltfg.menuabajovariant2.MenuAbajoVariant2

/**
 * Pantalla del gimnasio que muestra las rutinas disponibles.
 *
 * Esta pantalla muestra una lista de rutinas disponibles en la app. Cada rutina se representa como
 * un elemento en una lista vertical y contiene información como el nombre y una imagen opcional.
 *
 * @param navController El NavController para la navegación entre pantallas.
 * @param rutinaVM El ViewModel que proporciona los datos de las rutinas.
 */
@Composable
fun GymScreen(
    navController: NavController,
    rutinaVM: RutinaVM
) {

    val rutinas by rutinaVM.rutinasData.collectAsState()
    Log.d("GymScreen", "Rutinas: $rutinas")

    LaunchedEffect(Unit) {
        rutinaVM.fetchRutinas()
    }
    BackHandler {
        navController.navigate(Routes.principalMenuScreen.routes)
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Box { ArribaGym() }
            LazyColumn {
                items(rutinas.size) { index ->
                    EjercicioComponent(
                        rutina = rutinas[index],
                        index = index,
                        onClick = { rutinaSeleccionada ->
                            rutinaVM.seleccionarRutina(rutinaSeleccionada)
                            navController.navigate(Routes.gymSpecifyScreen.routes)
                        }
                    )

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
                onGoHome2 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onUserGo2 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}

/**
 * Componente composable que representa un elemento de la lista de rutinas en la pantalla del gimnasio.
 *
 * @param rutina La rutina que se va a representar en este elemento.
 * @param index El índice del elemento en la lista.
 * @param onClick La acción que se ejecuta cuando se hace clic en este elemento.
 */
@Composable
fun EjercicioComponent(
    rutina: Rutinas,
    index: Int,
    onClick: (Rutinas) -> Unit
) {
    val backgroundColor = if (index % 2 == 0) {
        Color.LightGray
    } else {
        Color.Gray
    }

    val nombreCapitalizado = rutina.nombre.capitalize()

    Box(
        modifier = Modifier
            .clickable(
                onClick = {
                    onClick(rutina)
                }
            )
            .background(backgroundColor)
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (rutina.img != "") {
                Image(
                    painter = rememberImagePainter(rutina.img),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp)
                        .size(100.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp)
                        .size(80.dp)
                        .background(Color.Gray)
                ) {
                    Text(
                        text = "No Img",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
            }
            Text(
                text = nombreCapitalizado,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}
