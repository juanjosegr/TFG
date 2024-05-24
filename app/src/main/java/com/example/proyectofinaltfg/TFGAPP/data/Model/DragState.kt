package com.example.proyectofinaltfg.TFGAPP.data.Model
/**
 * Clase que representa el estado de un hábito durante la operación de arrastre.
 *
 * @property habit El hábito que se está arrastrando, o null si no hay ningún hábito en arrastre.
 * @property offsetX La posición horizontal del hábito arrastrado.
 * @property offsetY La posición vertical del hábito arrastrado.
 */
data class DragState(
    val habit: HabitModel? = null,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f
)