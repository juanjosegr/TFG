package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsView

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.DragDropComponents.DraggableHabit
import com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.DragDropComponents.DraggingHabit
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.DragDrop.DragDropViewModel
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM.UpdateHabitVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM.HabitScreenVM
import com.example.proyectofinaltfg.framedetextos.inter
import com.example.proyectofinaltfg.ui.theme.LightGreen
import com.example.proyectofinaltfg.ui.theme.Red40
/**
 * Columnas separadas que muestran los hábitos divididos en dos columnas según su estado "por hacer" o "hecha".
 *
 * @param navController El controlador de navegación de Jetpack Compose.
 * @param habitScreenVM El ViewModel que gestiona los hábitos.
 * @param updateHabitVM El ViewModel que gestiona la actualización de hábitos.
 * @param dragDropViewModel El ViewModel que gestiona el arrastre y la soltura de los hábitos.
 */
@Composable
fun ColumnasSeparadas(
    navController: NavController,
    habitScreenVM: HabitScreenVM,
    updateHabitVM: UpdateHabitVM,
    dragDropViewModel: DragDropViewModel
) {
    val habits by habitScreenVM.habitsData.collectAsState()
    val dragState by dragDropViewModel.dragState.collectAsState()

    Log.d("Compose,ColumnasSeparadas", "Number of notes: ${habits.size}")
    if (habits.isNotEmpty()) {
        val rightColumnNotes = habits.filter { it.hecha_hacer == "por hacer" }
        val leftColumnNotes = habits.filter { it.hecha_hacer == "hecha" }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Left side
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(8.dp)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->

                                },
                                onDrag = { change, dragAmount ->

                                },
                                onDragEnd = {
                                    dragDropViewModel.dropHabit(habitScreenVM, "por hacer")
                                },
                                onDragCancel = {

                                }
                            )
                        }
                ) {
                    Column {
                        for (habit in rightColumnNotes) {
                            DraggableHabit(
                                habit = habit,
                                backgroundColor = Red40,
                                textColor = Color.Black,
                                navController = navController,
                                updateHabitVM = updateHabitVM,
                                dragDropViewModel = dragDropViewModel,
                                habitScreenVM
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }


                Spacer(modifier = Modifier.width(16.dp))

                // Right side
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(8.dp)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->

                                },
                                onDrag = { change, dragAmount ->

                                },
                                onDragEnd = {
                                    dragDropViewModel.dropHabit(habitScreenVM, "por hacer")
                                },
                                onDragCancel = {

                                }
                            )
                        }
                ) {
                    Column {
                        for (habit in leftColumnNotes) {
                            DraggableHabit(
                                habit = habit,
                                backgroundColor = LightGreen,
                                textColor = Color.Black,
                                navController = navController,
                                updateHabitVM = updateHabitVM,
                                dragDropViewModel = dragDropViewModel,
                                habitScreenVM
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            if (dragState.habit != null) {
                DraggingHabit(
                    habit = dragState.habit!!,
                    offsetX = dragState.offsetX,
                    offsetY = dragState.offsetY
                )
            }
        }
    } else {
        Text("No hay hábitos disponibles.")
    }
}

/**
 * Componente de caja personalizado que muestra el hábito y permite su interacción.
 *
 * @param text El texto del hábito.
 * @param backgroundColor El color de fondo del componente.
 * @param textColor El color del texto del componente.
 * @param navController El controlador de navegación de Jetpack Compose.
 * @param updateHabitVM El ViewModel que gestiona la actualización de hábitos.
 * @param idHabit El ID del hábito.
 * @param modifier El modificador para aplicar al componente.
 */
@Composable
fun CustomTextBox(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    navController: NavController,
    updateHabitVM: UpdateHabitVM,
    idHabit: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = {
                    updateHabitVM.changeTextHabit(text)
                    val nuevaHechaHacer = if (updateHabitVM.hechaHacer == "hecha") "por hacer" else "hecha"
                    updateHabitVM.allDateObtains(text, idHabit,nuevaHechaHacer)
                    navController.navigate(Routes.habitUpdateScreen.routes)
                }),
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
                Text(
                    text,
                    color = textColor,
                    fontSize = 12.0.sp,
                    fontFamily = inter,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}