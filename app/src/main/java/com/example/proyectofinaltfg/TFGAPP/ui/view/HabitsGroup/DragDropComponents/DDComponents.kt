package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.DragDropComponents

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.TFGAPP.data.Model.HabitModel
import com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsView.CustomTextBox
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.DragDrop.DragDropViewModel
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM.UpdateHabitVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM.HabitScreenVM
import kotlin.math.roundToInt

/**
 * Composable para representar un hábito que puede ser arrastrado y soltado.
 *
 * @param habit El modelo del hábito que se está representando.
 * @param backgroundColor El color de fondo del cuadro de texto personalizado.
 * @param textColor El color del texto del cuadro de texto personalizado.
 * @param navController El NavController para la navegación entre pantallas.
 * @param updateHabitVM El ViewModel para actualizar los hábitos.
 * @param dragDropViewModel El ViewModel para gestionar el arrastre y la soltura.
 * @param habitScreenVM El ViewModel para la pantalla de hábitos.
 */
@Composable
fun DraggableHabit(
    habit: HabitModel,
    backgroundColor: Color,
    textColor: Color,
    navController: NavController,
    updateHabitVM: UpdateHabitVM,
    dragDropViewModel: DragDropViewModel,
    habitScreenVM: HabitScreenVM
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenCenterX = screenWidth / 2

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragDropViewModel.startDragging(habit, offset.x, offset.y)
                    },
                    onDrag = { change, dragAmount ->
                        change.consumeAllChanges()
                        dragDropViewModel.updateDragging(
                            dragDropViewModel.dragState.value.offsetX + dragAmount.x,
                            dragDropViewModel.dragState.value.offsetY + dragAmount.y
                        )

                        val isDraggedToRight = dragDropViewModel.dragState.value.offsetX > screenCenterX.toPx()
                        val dropTarget = if (isDraggedToRight) "hecha" else "por hacer"
                        dragDropViewModel.updateDropTarget(dropTarget)
                    },
                    onDragEnd = {
                        val dropTarget = dragDropViewModel.dropTarget.value
                        dragDropViewModel.dropHabit(habitScreenVM, dropTarget)
                    },
                    onDragCancel = {
                        dragDropViewModel.stopDragging()
                    }
                )
            }
    ) {
        CustomTextBox(
            text = habit.Habit,
            backgroundColor = backgroundColor,
            textColor = textColor,
            navController = navController,
            updateHabitVM = updateHabitVM,
            idHabit = habit.idHabit
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}


/**
 * Composable para representar un hábito mientras se está arrastrando.
 *
 * @param habit El modelo del hábito que se está arrastrando.
 * @param offsetX La posición horizontal del hábito arrastrado.
 * @param offsetY La posición vertical del hábito arrastrado.
 */
@Composable
fun DraggingHabit(habit: HabitModel, offsetX: Float, offsetY: Float) {
    CustomTextBox(
        text = habit.Habit,
        backgroundColor = Color.Gray,
        textColor = Color.White,
        navController = NavController(LocalContext.current),
        updateHabitVM = UpdateHabitVM(),
        idHabit = habit.idHabit,
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .alpha(0.5f)
    )
}