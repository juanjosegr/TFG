package com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryGroup.DiaryUpdateView

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiaryUpdateVM.UpdateNoteVM
import com.example.proyectofinaltfg.arriba.Arriba
import com.example.proyectofinaltfg.menuabajovariant2.MenuAbajoVariant2
/**
 * Pantalla para actualizar una nota en el diario.
 *
 * @param navController El NavController para la navegación entre pantallas.
 * @param updateNoteVM El ViewModel para actualizar notas.
 */
@Composable
fun DiaryUpdateScreen(navController: NavController, updateNoteVM: UpdateNoteVM) {
    BackHandler {
        navController.navigate(Routes.diarioScreen.routes)
    }
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
                UpdateNoteComponent(updateNoteVM = updateNoteVM)
            }
            LlamadaShowAler(
                navController,
                updateNoteVM,
                updateNoteVM.textError,
                updateNoteVM.casoErrorAcierto,
            )
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