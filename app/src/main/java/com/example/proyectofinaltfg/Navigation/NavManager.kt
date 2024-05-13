package com.example.proyectofinaltfg.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.view.ApiView.ApiScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.CalendaryView.CalendaryScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryPackage.AnadirNotaView.AddNoteScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryPackage.DiarioView.DiarioPrincipalScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryPackage.DiaryUpdateView.DiaryUpdateScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.GymView.GymScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsView.HabtisScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.UserPackage.LoginView.LoginScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.UserPackage.RegisterView.RegisterScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.UserPackage.UserView.UserScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.MenuPrincipalView.PrincipalMenuScreen
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.AddNoteVM.AddNoteVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiarioVM.DiaryScreenVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiaryUpdateVM.UpdateNoteVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.LoginRegisterVM

@Composable
fun NavManager(
    loginScreenVM: LoginRegisterVM,
    diaryScreenVM: DiaryScreenVM,
    addNoteVM: AddNoteVM,
    updateNoteVM: UpdateNoteVM
) {
    val navController = rememberNavController()

    NavHost(
        //navController = navController, startDestination = Routes.loginScreen.routes
        navController = navController, startDestination = Routes.principalMenuScreen.routes
    ) {
        composable(Routes.loginScreen.routes) {
            LoginScreen(navController, loginScreenVM)
        }
        composable(Routes.registerScreen.routes) {
            RegisterScreen(navController, loginScreenVM)
        }
        composable(Routes.principalMenuScreen.routes){
            PrincipalMenuScreen(navController)
        }
        composable(Routes.diarioScreen.routes) {
            DiarioPrincipalScreen(navController, diaryScreenVM, updateNoteVM)
        }
        composable(Routes.anadirScreen.routes) {
            AddNoteScreen(addNoteVM)
        }
        composable(Routes.diaryUpdateScren.routes) {
            DiaryUpdateScreen(navController, updateNoteVM)
        }
        composable(Routes.userScren.routes) {
            UserScreen(navController)
        }
        composable(Routes.apiScreen.routes) {
            ApiScreen(navController)
        }
        composable(Routes.calendaryScreen.routes) {
            CalendaryScreen(navController)
        }
        composable(Routes.gymScreen.routes) {
            GymScreen(navController)
        }
        composable(Routes.habtisScreen.routes) {
            HabtisScreen(navController)
        }
    }
}