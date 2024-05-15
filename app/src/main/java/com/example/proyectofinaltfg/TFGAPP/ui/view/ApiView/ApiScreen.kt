package com.example.proyectofinaltfg.TFGAPP.ui.view.ApiView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.proyectofinaltfg.arribaaleatorio.ArribaAleatorio
import com.example.proyectofinaltfg.menuabajovariant5.MenuAbajoVariant5

@Composable
fun ApiScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Box {
                ArribaAleatorio()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-50).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

            }

        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoVariant5(
                onHomeGo5 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onLikeBtn = { },
                onUserGo5 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}