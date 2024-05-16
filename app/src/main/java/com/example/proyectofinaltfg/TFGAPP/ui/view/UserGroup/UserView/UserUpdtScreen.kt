package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.UserView

import androidx.activity.compose.BackHandler
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
import com.example.proyectofinaltfg.menuabajovariant3.MenuAbajoVariant3
import com.example.proyectofinaltfg.userfot.UserFot
import com.example.proyectofinaltfg.usertop.UserTop


@Composable
fun UserUpdtScreen(
    navController: NavController
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
                UserTop()
            }
            UserFot()
            UsuerCampsMod()
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoVariant3(
                onHomeGo3 = { navController.navigate(Routes.userScren.routes) },
                onBtnDesconectar = {  },
                onactuBtn = { }
            )
        }
    }
}