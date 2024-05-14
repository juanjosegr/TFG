package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.LoginView

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

@Composable
fun LoginScreen(navController: NavController, loginScreenVM: LoginRegisterVM) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Arriba()
            GrupoLoginNuevo(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.5f),
                loginScreenVM = loginScreenVM,
                onBtnLogin = { loginScreenVM.login { navController.navigate(Routes.principalMenuScreen.routes) } },
                onRegistedTap = {
                    loginScreenVM.resetFields()
                    navController.navigate(Routes.registerScreen.routes)
                },
                passwordVisible = loginScreenVM.passwordVisible
            )
            LlamadaShowAler(loginScreenVM, loginScreenVM.textError, loginScreenVM.casoErrorAcierto)
        }
    }
}