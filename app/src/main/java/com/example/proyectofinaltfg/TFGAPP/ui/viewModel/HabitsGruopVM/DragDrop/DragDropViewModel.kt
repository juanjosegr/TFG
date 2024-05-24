package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.DragDrop

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyectofinaltfg.TFGAPP.data.Model.DragState
import com.example.proyectofinaltfg.TFGAPP.data.Model.HabitModel
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM.HabitScreenVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
/**
 * ViewModel para gestionar el estado de arrastre y soltar de los hábitos.
 */
class DragDropViewModel : ViewModel() {
    private val _dragState = MutableStateFlow(DragState())
    val dragState: StateFlow<DragState> = _dragState

    private val _dropTarget = mutableStateOf("")
    val dropTarget: State<String> = _dropTarget
    /**
     * Inicia el arrastre de un hábito.
     *
     * @param habit El hábito que se está arrastrando.
     * @param offsetX La posición horizontal inicial del arrastre.
     * @param offsetY La posición vertical inicial del arrastre.
     */
    fun startDragging(habit: HabitModel, offsetX: Float, offsetY: Float) {
        _dragState.value = DragState(habit, offsetX, offsetY)
    }
    /**
     * Actualiza la posición del hábito que se está arrastrando.
     *
     * @param offsetX La nueva posición horizontal del arrastre.
     * @param offsetY La nueva posición vertical del arrastre.
     */
    fun updateDragging(offsetX: Float, offsetY: Float) {
        _dragState.value = _dragState.value.copy(offsetX = offsetX, offsetY = offsetY)
    }
    /**
     * Detiene el arrastre del hábito.
     */
    fun stopDragging() {
        _dragState.value = DragState()
    }
    /**
     * Actualiza el objetivo arrastrado basado en la posición del hábito arrastrado.
     *
     * @param target El nuevo objetivo de caída (por ejemplo, "hecha" o "por hacer").
     */
    fun updateDropTarget(target: String) {
        _dropTarget.value = target
    }
    /**
     * Suelta el hábito en la columna objetivo y actualiza el estado del hábito.
     *
     * @param habitScreenVM El ViewModel que gestiona los hábitos.
     * @param targetColumn La columna objetivo donde se suelta el hábito.
     */
    fun dropHabit(habitScreenVM: HabitScreenVM, targetColumn: String) {
        _dragState.value.habit?.let { habit ->
            habitScreenVM.updateHabitStatus(habit.idHabit, targetColumn)
        }
        stopDragging()
    }
}
