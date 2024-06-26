package com.example.proyectofinaltfg.TFGAPP.ui.view.CalendaryView

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.arribacalendario.ArribaCalendario
import com.example.proyectofinaltfg.menuabajovariant2.MenuAbajoVariant2
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
/**
 * Composable que representa la pantalla del calendario.
 *
 * @param navController El NavController para la navegación entre pantallas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendaryScreen(
    navController: NavController
) {
    BackHandler {
        navController.navigate(Routes.principalMenuScreen.routes)
    }
    // Estado para el seleccionador de fecha
    val state = rememberDatePickerState()
    // Obtiene la fecha seleccionada
    val selectedDate = state.selectedDateMillis?.let { Date(it) }
    // Formateador de fecha
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Box {
                ArribaCalendario()
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-50).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Seleccionador de fecha
                DatePicker(state = state)
                // Botón para ir a la fecha seleccionada
                Button(onClick = {
                    selectedDate?.let {
                        val formattedDate = formatter.format(it)
                        navController.navigate("TargetScreen/$formattedDate")
                    }
                }) {
                    Text("Ir a la fecha")
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