package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsAddView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            OnDissmisClicl = { addHabitVM.closedShowAlert() }
        )
    }
}
@Composable
fun AddNoteComponents(
    addHabitVM: AddHabitVM
) {
    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = addHabitVM.textHabit,
            onValueChange = { addHabitVM.changeTextHabit(it) },
            label = { Text("Habito") },
            modifier = Modifier.height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(
                onClick = {
                    addHabitVM.saveNewHabit()

                }) {
                Text("Aceptar")
            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .width(50.dp)
            )
        }
        LlamadaShowAler(addHabitVM = addHabitVM, text = "Creada Correctamente" , caso = "Creada")
    }
}