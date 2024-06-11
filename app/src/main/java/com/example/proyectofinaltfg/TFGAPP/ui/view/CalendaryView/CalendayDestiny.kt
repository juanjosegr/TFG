package com.example.proyectofinaltfg.TFGAPP.ui.view.CalendaryView

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiarioVM.DiaryScreenVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM.HabitScreenVM
import com.example.proyectofinaltfg.arribacalendario.ArribaCalendario
import com.example.proyectofinaltfg.menuabajovariant2.MenuAbajoVariant2
import com.example.proyectofinaltfg.ui.theme.BlueOcean
import com.example.proyectofinaltfg.ui.theme.LightGreen
import com.example.proyectofinaltfg.ui.theme.RedOrange
import com.example.proyectofinaltfg.ui.theme.RedPink
import com.example.proyectofinaltfg.ui.theme.Violet
/**
 * Composable que representa la pantalla de destino del calendario para una fecha específica.
 *
 * @param navController El NavController para la navegación entre pantallas.
 * @param date La fecha seleccionada en el calendario en formato "yyyy-MM-dd".
 * @param diaryScreenVM El ViewModel para gestionar los datos de las notas.
 * @param habitScreenVM El ViewModel para gestionar los datos de los hábitos.
 */
@Composable
fun CalendaryDestiny(
    navController: NavController,
    date: String,
    diaryScreenVM: DiaryScreenVM,
    habitScreenVM: HabitScreenVM
) {
    BackHandler {
        navController.navigate(Routes.calendaryScreen.routes)
    }
    // Recolecta las notas y hábitos asociados a la fecha seleccionada
    val notes by diaryScreenVM.notesData.collectAsState()
    val habits by habitScreenVM.habitsData.collectAsState()

    // Realiza operaciones de efecto lanzado cuando cambia la fecha
    LaunchedEffect(date) {
        Log.d("TargetScreen", "LaunchedEffect with date: $date")
        diaryScreenVM.fetchNotesDate(date)
        habitScreenVM.fetchHabitsDate(date)
    }

    Log.d("TargetScreen", "Notes size: ${notes.size}")
    Log.d("TargetScreen", "Habits size: ${habits.size}")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Box {
                ArribaCalendario()
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(
                        ScrollState(100000),
                        enabled = true,
                        reverseScrolling = true
                    )
            ) {
                // Sección de notas
                Text("Notas")
                notes.forEach { note ->
                    val backgroundColor: Color =
                        when (note.noteColorIndex["value-s-VKNKU"] as? Long) {
                            -92835718103040 -> RedOrange
                            -25378210132787200 -> LightGreen
                            -53606925635420160 -> BlueOcean
                            -3219709348544512 -> RedPink
                            -13911192913313792 -> Violet
                            else -> Color.White
                        }
                    CustomNotesBox(
                        title = note.title,
                        text = note.note,
                        backgroundColor = backgroundColor,
                        titleColor = Color.Black,
                        textColor = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sección de hábitos
                Text("Hábitos")
                habits.forEach { habit ->
                    CustomHabitsBox(
                        text = habit.Habit,
                        backgroundColor = LightGreen,
                        textColor = Color.Black
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoVariant2(
                onGoHome2 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onUserGo2 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}
