package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsUpdate

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.view.GenericComponent.ShowAlert
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM.UpdateHabitVM


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