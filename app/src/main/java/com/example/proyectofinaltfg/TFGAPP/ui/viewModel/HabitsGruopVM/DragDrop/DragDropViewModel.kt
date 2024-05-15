package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.DragDrop

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyectofinaltfg.TFGAPP.data.Model.DragState
import com.example.proyectofinaltfg.TFGAPP.data.Model.HabitModel
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM.HabitScreenVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DragDropViewModel : ViewModel() {
    private val _dragState = MutableStateFlow(DragState())
    val dragState: StateFlow<DragState> = _dragState

    private val _dropTarget = mutableStateOf("")
    val dropTarget: State<String> = _dropTarget

    fun startDragging(habit: HabitModel, offsetX: Float, offsetY: Float) {
        _dragState.value = DragState(habit, offsetX, offsetY)
    }

    fun updateDragging(offsetX: Float, offsetY: Float) {
        _dragState.value = _dragState.value.copy(offsetX = offsetX, offsetY = offsetY)
    }

    fun stopDragging() {
        _dragState.value = DragState()
    }

    fun updateDropTarget(target: String) {
        _dropTarget.value = target
    }

    fun dropHabit(habitScreenVM: HabitScreenVM, targetColumn: String) {
        _dragState.value.habit?.let { habit ->
            habitScreenVM.updateHabitStatus(habit.idHabit, targetColumn)
        }
        stopDragging()
    }
}
