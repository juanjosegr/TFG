package com.example.proyectofinaltfg.Navigation.Routes

/**
 * Sealed class que define las rutas de las pantallas de la aplicación.
 * Cada objeto dentro de esta clase sellada representa una pantalla específica
 * con su correspondiente ruta.
 */
sealed class Routes (val routes: String) {
    object loginScreen: Routes ("LoginScreen")
    object registerScreen: Routes ("RegisterScreen")
    object diarioScreen: Routes ("DiarioPrincipalScreen")
    object anadirScreen: Routes ("AnadirNotaScreen")
    object diaryUpdateScren: Routes ("DiaryUpdateScreen")
    object principalMenuScreen: Routes ("PrincipalMenuScreen")
    object userScren : Routes ("UserScreen")
    object apiScreen : Routes("ApiScreen")
    object ApiLikeScreen : Routes("ApiLikeScreen")
    object calendaryScreen : Routes("CalendaryScreen")
    object gymScreen : Routes("GymScreen")
    object gymSpecifyScreen : Routes("gymSpecifyScreen")
    object habtisScreen : Routes("HabtisScreen")
    object addHabitScreen : Routes("AddHabitScreen")
    object habitUpdateScreen : Routes("HabitUpdateScreen")
    data class targetScreen(val date: String) : Routes("TargetScreen/$date")
    object userUpdtScreen : Routes ("UserUpdtScreen")
}