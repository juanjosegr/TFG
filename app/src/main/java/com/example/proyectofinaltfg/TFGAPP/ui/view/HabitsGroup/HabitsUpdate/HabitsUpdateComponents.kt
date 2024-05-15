package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsUpdate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun ShowHabitComponent(
    updateHabitsVM: UpdateHabitVM
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = updateHabitsVM.textHabit,
            onValueChange = { updateHabitsVM.changeTextHabit(it) },
            label = { Text("Nota") },
            modifier = Modifier.height(200.dp)
        )
    }
}


@Composable
fun GrupoActualizarHabitoMod(
    modifier: Modifier = Modifier,
    onBtnAnadir: () -> Unit = {},
    onBrnDelete: () -> Unit = {}
) {
    TopLevel(modifier = modifier) {
        ActualizairBtn(onBtnAnadir = onBtnAnadir) {
            Rectangle8(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
            Actualizar(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -2.0.dp,
                        y = 0.0.dp
                    )
                )
            )
        }
        BorrarBtn(onBtnAnadir = onBtnAnadir) {
            Rtt8(
                onBrnDelete = onBrnDelete,
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
            BorrarBtnhabito(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -2.0.dp,
                        y = 0.0.dp
                    )
                )
            )
        }
    }
}