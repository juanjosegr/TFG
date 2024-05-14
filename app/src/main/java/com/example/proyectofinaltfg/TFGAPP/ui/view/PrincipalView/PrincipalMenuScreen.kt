package com.example.proyectofinaltfg.TFGAPP.ui.view.PrincipalView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.agenadatop.AgenadaTop

@Composable
fun PrincipalMenuScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            AgenadaTop()
            Agrupadoss(
                onGroupDiary = { navController.navigate(Routes.diarioScreen.routes)},
                onGroupHabits = {navController.navigate(Routes.habtisScreen.routes) },
                onGroupCalendary = {navController.navigate(Routes.calendaryScreen.routes) },
                onGroupTraining = {navController.navigate(Routes.gymScreen.routes) },
                onGroupAleatory = {navController.navigate(Routes.apiScreen.routes) }
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoaVariant2(
                onUserGo2 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}
