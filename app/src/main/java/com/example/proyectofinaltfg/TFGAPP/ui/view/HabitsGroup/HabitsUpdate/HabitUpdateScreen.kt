package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsUpdate

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
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM.UpdateHabitVM
import com.example.proyectofinaltfg.arribahabitos.ArribaHabitos
import com.example.proyectofinaltfg.grupoactualizarhabito.GrupoActualizarHabito
import com.example.proyectofinaltfg.menuabajovariant2.MenuAbajoVariant2
/**
 * Pantalla de actualización de hábitos donde los usuarios pueden modificar un hábito existente.
 *
 * @param navController El controlador de navegación de Jetpack Compose.
 * @param updateHabitVM El ViewModel que gestiona la actualización de hábitos.
 */
@Composable
fun HabitUpdateScreen(
    navController: NavController,
    updateHabitVM: UpdateHabitVM,
) {
    BackHandler {
        navController.navigate(Routes.habtisScreen.routes)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Box {
                ArribaHabitos()
            }
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-100).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ShowHabitComponent(updateHabitVM)
                GrupoActualizarHabito(
                    onBtnActu = { updateHabitVM.updateHabitVM(updateHabitVM.idHabit) },
                    onBtnDelete = { updateHabitVM.deleteHabitVM(updateHabitVM.idHabit) })
            }
            LlamadaShowAler(
                navController,
                updateHabitVM,
                updateHabitVM.textError,
                updateHabitVM.casoErrorAcierto,
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoVariant2(
                onGoHome2 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onUserGo2 = { navController.navigate(Routes.userScren.routes) })
        }
    }
}
