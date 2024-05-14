package com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryGroup.AnadirNotaView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.AddNoteVM.AddNoteVM
import com.example.proyectofinaltfg.arriba.Arriba

@Composable
fun AddNoteScreen(addNoteVM: AddNoteVM) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Arriba()
            AddNoteComponents(addNoteVM)
            LlamadaShowAler(addNoteVM, addNoteVM.textError, addNoteVM.casoErrorAcierto)
        }
    }
}