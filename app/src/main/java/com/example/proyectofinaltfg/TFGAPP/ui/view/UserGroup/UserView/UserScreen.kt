package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.UserView

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.UserProfileVM
import com.example.proyectofinaltfg.menuabajovariant3.MenuAbajoVariant3
import com.example.proyectofinaltfg.usertop.UserTop
/**
 * Pantalla de perfil de usuario.
 *
 * @param navController Controlador de navegación de Jetpack Compose.
 * @param userProfileVM ViewModel que contiene los datos del perfil de usuario.
 */
@Composable
fun UserScreen(
    navController: NavController,
    userProfileVM: UserProfileVM
) {
    BackHandler {
        navController.navigate(Routes.principalMenuScreen.routes)
    }
    LaunchedEffect(key1 = true) {
        userProfileVM.fetchUser()
    }

    DisposableEffect(key1 = true) {
        onDispose {
            userProfileVM.fetchUser()
        }
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
            ProfileScreenNoEdit(userProfileVM)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoVariant3(
                onHomeGo3 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onBtnDesconectar = { navController.navigate(Routes.loginScreen.routes) },
                onactuBtn = { navController.navigate(Routes.userUpdtScreen.routes) }
            )
        }
    }
}
