package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.RegisterView

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.LoginRegisterVM
import com.example.proyectofinaltfg.arriba.Arriba
/**
 * Pantalla de registro que permite a los usuarios crear una nueva cuenta en la aplicación.
 *
 * @param navController Controlador de navegación para la navegación entre destinos.
 * @param loginScreenVM ViewModel que maneja la lógica de inicio de sesión y registro.
 */
@Composable
fun RegisterScreen(
    navController: NavController,
    loginScreenVM: LoginRegisterVM
) {
    BackHandler {
        navController.navigate(Routes.loginScreen.routes)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Arriba()
            GrupoRegistroNuevo(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.5f),
                loginScreenVM = loginScreenVM,
                onBtnAcept = {
                    loginScreenVM.createUser { navController.navigate(Routes.loginScreen.routes) }
                },
                passwordVisible = loginScreenVM.passwordVisible
            )
            LlamadaShowAlert(
                loginScreenVM,
                loginScreenVM.textError,
                loginScreenVM.casoErrorAcierto
            )
        }
    }
}