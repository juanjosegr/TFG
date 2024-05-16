package com.example.proyectofinaltfg.TFGAPP.ui.view.ApiView

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.ApiVM.ApiVM
import com.example.proyectofinaltfg.arribaaleatorio.ArribaAleatorio
import com.example.proyectofinaltfg.dislike.Dislike
import com.example.proyectofinaltfg.menuabajoaleatorio.MenuAbajoAleatorio
import com.example.proyectofinaltfg.reload.Reload
import com.google.relay.compose.BoxScopeInstanceImpl.align

@Composable
fun ApiLikeScreen(
    navController: NavController,
    apiVM: ApiVM
) {

    BackHandler {
        navController.navigate(Routes.principalMenuScreen.routes)
    }

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
                //Imprimir las fotos aqui
            }
            Row {
                Dislike()
                Spacer(modifier = Modifier.width(70.dp))
                Reload()
            }

        }

    }
    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .offset(y = (-46).dp)
    ) {
        MenuAbajoAleatorio(
            onGoHomeAlet = { navController.navigate(Routes.principalMenuScreen.routes) },
            onLikeGo = { navController.navigate(Routes.apiScreen.routes) },
            onUserGo = { navController.navigate(Routes.userScren.routes) }
        )
    }
}
