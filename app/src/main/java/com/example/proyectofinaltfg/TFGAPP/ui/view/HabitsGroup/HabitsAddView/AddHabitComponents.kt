package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsAddView

import androidx.compose.runtime.Composable
import com.example.proyectofinaltfg.TFGAPP.ui.view.GenericComponent.ShowAlert
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsAddVM.AddHabitVM


@Composable
fun LlamadaShowAler(addHabitVM: AddHabitVM, text: String, caso: String) {
    if (addHabitVM.showAlert) {
        ShowAlert(
            caso,
            text,
            "Aceptar",
            onAcceptClick = { addHabitVM.closedShowAlert() },
            OnDissmisClicl = { }
        )
    }
}
