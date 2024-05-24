package com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryGroup.DiaryUpdateView

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.data.Model.NotaModel
import com.example.proyectofinaltfg.TFGAPP.ui.view.GenericComponent.ShowAlert
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiaryUpdateVM.UpdateNoteVM

/**
 * Composable que muestra los componentes para actualizar una nota.
 *
 * @param updateNoteVM El ViewModel para actualizar notas.
 */
@Composable
fun UpdateNoteComponent(
    updateNoteVM: UpdateNoteVM
) {
    // Estado para controlar la expansión del menú desplegable de colores
    var expanded by remember { mutableStateOf(false) }
    // Estado para almacenar el nombre del color seleccionado
    var selectedColorName by remember { mutableStateOf("Elegir color") }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = updateNoteVM.titleNote,
            onValueChange = { updateNoteVM.changeTitleNote(it) },
            label = { Text("Título") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = updateNoteVM.textNote,
            onValueChange = { updateNoteVM.changeTextNote(it) },
            label = { Text("Nota") },
            modifier = Modifier.height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = { updateNoteVM.updateNote(updateNoteVM.idDoc) }) {
                Text("Aceptar")
            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .width(50.dp)
            )

            Surface(
                modifier = Modifier.clickable { expanded = true },
                shape = RoundedCornerShape(4.dp),
                color = MaterialTheme.colors.primary,
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Row(
                    modifier = Modifier.padding(9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = selectedColorName, color = Color.White, fontSize = 16.sp)
                    Icon(Icons.Default.ArrowDropDown, "dropdown", tint = Color.White)
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                NotaModel.noteColors.zip(updateNoteVM.colorNames).forEach { (color, name) ->
                    DropdownMenuItem(onClick = {
                        updateNoteVM.changeColorNote(color)
                        selectedColorName = name
                        expanded = false
                    }) {
                        Text(text = name)
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(225.dp)
                .offset(x = 15.dp)
        ) {
            Button(onClick = { updateNoteVM.deleteNote(idDoc = updateNoteVM.idDoc) }) {
                Text("Borrar")
            }
        }
    }
}
/**
 * Composable para mostrar un cuadro de diálogo de alerta en función de las acciones realizadas en la actualización de la nota.
 *
 * @param navController El NavController para la navegación entre pantallas.
 * @param updateNoteVM El ViewModel para actualizar notas.
 * @param text El texto que se mostrará en el cuadro de diálogo de alerta.
 * @param caso El caso de la acción que desencadena el cuadro de diálogo de alerta.
 */
@Composable
fun LlamadaShowAler(
    navController: NavController,
    updateNoteVM: UpdateNoteVM,
    text: String,
    caso: String
) {
    if (updateNoteVM.showAlert) {
        ShowAlert(
            caso,
            text,
            "Aceptar",
            onAcceptClick = {
                updateNoteVM.closedShowAlert()
                navController.navigate(Routes.diarioScreen.routes)
            },
            OnDissmisClicl = { }
        )
    }
}