package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsView

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.DragDrop.DragDropViewModel
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM.UpdateHabitVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM.HabitScreenVM
import com.example.proyectofinaltfg.arribahabitos.ArribaHabitos
import com.example.proyectofinaltfg.hacerhecho.HacerHecho
import com.example.proyectofinaltfg.menuabajodefault.MenuAbajoDefault

@Composable
fun HabtisScreen(
    navController: NavController,
    habitScreenVM: HabitScreenVM,
    updateHabitVM: UpdateHabitVM,
    dragDropViewModel: DragDropViewModel
) {
    BackHandler {
        navController.navigate(Routes.principalMenuScreen.routes)
    }
    LaunchedEffect(Unit) {
        habitScreenVM.fetchHabits()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            ArribaHabitos()
            HacerHecho(modifier = Modifier.offset(x = -8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        ScrollState(100000),
                        enabled = true,
                        reverseScrolling = true
                    )
            ) {
                ColumnasSeparadas(navController, habitScreenVM, updateHabitVM, dragDropViewModel)
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoDefault(
                onHomeGo1 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onAddTap1 = { navController.navigate(Routes.addHabitScreen.routes) },
                onUsergo1 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}