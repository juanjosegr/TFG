package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsUpdate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.view.GenericComponent.ShowAlert
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM.UpdateHabitVM
import com.example.proyectofinaltfg.grupoactualizarhabito.ActualizairBtn
import com.example.proyectofinaltfg.grupoactualizarhabito.Actualizar
import com.example.proyectofinaltfg.grupoactualizarhabito.BorrarBtn
import com.example.proyectofinaltfg.grupoactualizarhabito.BorrarBtnhabito
import com.example.proyectofinaltfg.grupoactualizarhabito.Rectangle8
import com.example.proyectofinaltfg.grupoactualizarhabito.Rtt8
import com.example.proyectofinaltfg.grupoactualizarhabito.TopLevel
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.tappable
/**
 * Muestra una alerta en la pantalla cuando se produce un evento específico en la gestión de hábitos.
 *
 * @param navController El controlador de navegación de Jetpack Compose.
 * @param updateHabitsVM El ViewModel que gestiona la actualización de hábitos.
 * @param text El texto que se mostrará en la alerta.
 * @param caso El caso específico que desencadenó la alerta.
 */
@Composable
fun LlamadaShowAler(
    navController: NavController,
    updateHabitsVM: UpdateHabitVM,
    text: String,
    caso: String
) {
    if (updateHabitsVM.showAlert) {
        ShowAlert(
            caso,
            text,
            "Aceptar",
            onAcceptClick = {
                updateHabitsVM.closedShowAlert()
                navController.navigate(Routes.habtisScreen.routes)
            },
            OnDissmisClicl = { }
        )
    }
}
/**
 * Componente para mostrar y actualizar un hábito en la pantalla.
 *
 * @param updateHabitsVM El ViewModel que gestiona la actualización de hábitos.
 */
@Composable
fun ShowHabitComponent(
    updateHabitsVM: UpdateHabitVM
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = updateHabitsVM.textHabit,
            onValueChange = { updateHabitsVM.changeTextHabit(it) },
            label = { Text("Hábito") },
            modifier = Modifier
                .height(80.dp)
                .padding(top = 25.dp)
        )
    }
}

