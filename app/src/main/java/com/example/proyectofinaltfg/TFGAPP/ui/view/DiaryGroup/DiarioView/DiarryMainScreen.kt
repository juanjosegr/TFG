package com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryGroup.DiarioView

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiarioVM.DiaryScreenVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiaryUpdateVM.UpdateNoteVM
import com.example.proyectofinaltfg.menuabajodefault.MenuAbajoDefault

@Composable
fun DiarioPrincipalScreen(
    navController: NavController,
    diaryScreenVM: DiaryScreenVM,
    updateNoteVM: UpdateNoteVM
) {
    Log.d("Compose", "DiarioPrincipalScreen")
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .verticalScroll(
                    ScrollState(100000),
                    enabled = true,
                    reverseScrolling = true
                )
        ) {
            Box {
                ArribaDiarioNuevo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .align(Alignment.TopStart),
                    diaryScreenVM
                )
            }
            ColumnasSeparadas(navController, diaryScreenVM, updateNoteVM)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoDefault(
                onHomeGo1 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onAddTap1 = { navController.navigate(Routes.anadirScreen.routes) },
                onUsergo1 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}
