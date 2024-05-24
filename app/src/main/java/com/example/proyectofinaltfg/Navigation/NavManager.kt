package com.example.proyectofinaltfg.Navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.data.Model.Rutinas
import com.example.proyectofinaltfg.TFGAPP.ui.view.ApiView.ApiLikeScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.ApiView.ApiScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.CalendaryView.CalendaryScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryGroup.AnadirNotaView.AddNoteScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryGroup.DiarioView.DiarioPrincipalScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.DiaryGroup.DiaryUpdateView.DiaryUpdateScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.GymView.GymScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsAddView.AddHabitScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsUpdate.HabitUpdateScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsView.HabtisScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.PrincipalView.PrincipalMenuScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.LoginView.LoginScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.RegisterView.RegisterScreen
import com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.UserView.UserScreen
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.AddNoteVM.AddNoteVM
import com.example.proyectofinaltfg.TFGAPP.ui.view.CalendaryView.CalendaryDestiny
import com.example.proyectofinaltfg.TFGAPP.ui.view.GymView.GymSpecifyScren
import com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.UserView.UserUpdtScreen
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.ApiVM.ApiVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiarioVM.DiaryScreenVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiaryUpdateVM.UpdateNoteVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.GymVM.RutinaVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.DragDrop.DragDropViewModel
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsAddVM.AddHabitVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM.UpdateHabitVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM.HabitScreenVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.LoginRegisterVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.UserProfileVM

/**
 * Componente que gestiona la navegación dentro de la aplicación.
 * Este componente utiliza un [NavHost] para definir las diferentes rutas y pantallas
 * disponibles en la aplicación y asignarles las respectivas funciones y ViewModels.
 *
 * @param loginScreenVM ViewModel para la pantalla de inicio de sesión y registro.
 * @param diaryScreenVM ViewModel para la pantalla principal del diario.
 * @param addNoteVM ViewModel para la pantalla de añadir nota al diario.
 * @param updateNoteVM ViewModel para la pantalla de actualización de nota del diario.
 * @param addHabitVM ViewModel para la pantalla de añadir hábito.
 * @param updateHabitVM ViewModel para la pantalla de actualización de hábito.
 * @param habitScreenVM ViewModel para la pantalla de hábitos.
 * @param dragDropViewModel ViewModel para la funcionalidad de arrastrar y soltar.
 * @param apiVM ViewModel para la integración con la API.
 * @param userProfileVM ViewModel para el perfil de usuario.
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavManager(
    loginScreenVM: LoginRegisterVM,
    diaryScreenVM: DiaryScreenVM,
    addNoteVM: AddNoteVM,
    updateNoteVM: UpdateNoteVM,
    addHabitVM: AddHabitVM,
    updateHabitVM: UpdateHabitVM,
    habitScreenVM: HabitScreenVM,
    dragDropViewModel: DragDropViewModel,
    apiVM: ApiVM,
    userProfileVM: UserProfileVM,
    rutinaVM: RutinaVM
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Routes.loginScreen.routes
    ) {

        //User y login
        composable(Routes.loginScreen.routes) {
            LoginScreen(navController, loginScreenVM)
        }
        composable(Routes.registerScreen.routes) {
            RegisterScreen(navController, loginScreenVM)
        }
        composable(Routes.userScren.routes) {
            UserScreen(navController,userProfileVM)
        }
        composable(Routes.userUpdtScreen.routes) {
            UserUpdtScreen(navController,userProfileVM)
        }

        //Menu Principal
        composable(Routes.principalMenuScreen.routes){
            PrincipalMenuScreen(navController)
        }

       //Diario
        composable(Routes.diarioScreen.routes) {
            DiarioPrincipalScreen(navController, diaryScreenVM, updateNoteVM)
        }
        composable(Routes.anadirScreen.routes) {
            AddNoteScreen(addNoteVM,navController)
        }
        composable(Routes.diaryUpdateScren.routes) {
            DiaryUpdateScreen(navController, updateNoteVM)
        }


        //Api
        composable(Routes.apiScreen.routes) {
            ApiScreen(navController)
        }
        composable(Routes.ApiLikeScreen.routes) {
            ApiLikeScreen(navController, apiVM )
        }

       //Calendary
        composable(Routes.calendaryScreen.routes) {
            CalendaryScreen(navController)
        }
        // Esta función composable define una pantalla en el flujo de navegación que espera recibir una fecha como parte de su ruta.
        // La fecha se utilizará para determinar el destino específico en la pantalla CalendaryDestiny.
        composable(
            route = "TargetScreen/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            CalendaryDestiny(navController, date, diaryScreenVM, habitScreenVM)
        }


        //Gym
        composable(Routes.gymScreen.routes) {
            GymScreen(navController,rutinaVM)
        }
        composable(Routes.gymSpecifyScreen.routes) {
            GymSpecifyScren(navController,rutinaVM)
        }


        //Habits
        composable(Routes.habtisScreen.routes) {
            HabtisScreen(navController,habitScreenVM,updateHabitVM, dragDropViewModel)
        }
        composable(Routes.addHabitScreen.routes) {
            AddHabitScreen(navController, addHabitVM)
        }
        composable(Routes.habitUpdateScreen.routes) {
            HabitUpdateScreen(navController,updateHabitVM)
        }

    }
}