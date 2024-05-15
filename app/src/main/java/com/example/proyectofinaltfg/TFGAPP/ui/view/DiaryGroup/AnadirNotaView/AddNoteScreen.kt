package com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryGroup.AnadirNotaView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.AddNoteVM.AddNoteVM
import com.example.proyectofinaltfg.arriba.Arriba
import com.example.proyectofinaltfg.menuabajovariant2.MenuAbajoVariant2

@Composable
fun AddNoteScreen(addNoteVM: AddNoteVM, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Box {
                Arriba()
            }
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-50).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AddNoteComponents(addNoteVM)
            }
            LlamadaShowAler(
                addNoteVM,
                addNoteVM.textError,
                addNoteVM.casoErrorAcierto)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoVariant2(
                onGoHome2 = { navController.navigate(Routes.diarioScreen.routes) },
                onUserGo2 = { navController.navigate(Routes.userScren.routes) })
        }
    }
}