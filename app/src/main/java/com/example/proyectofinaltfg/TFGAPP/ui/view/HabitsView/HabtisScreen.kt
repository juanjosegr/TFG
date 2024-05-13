package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsView

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
import com.example.proyectofinaltfg.arribahabitos.ArribaHabitos
import com.example.proyectofinaltfg.menuabajodefault.MenuAbajoDefault

@Composable
fun HabtisScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            ArribaHabitos()
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoDefault(
                onHomeGo1 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onAddTap1 = {  },
                onUsergo1 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}