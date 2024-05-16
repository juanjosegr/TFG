package com.example.proyectofinaltfg.TFGAPP.ui.view.CalendaryView

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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

@Composable
fun CalendaryDestiny(
    navController: NavController,
    date: String,
    diaryScreenVM: DiaryScreenVM,
    habitScreenVM: HabitScreenVM
) {
    val notes by diaryScreenVM.notesData.collectAsState()
    val habits by habitScreenVM.habitsData.collectAsState()

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
            ) {
                Text("Notas")
                notes.forEach { note ->
                    Text(note.title)
                    Text(note.note)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Hábitos")
                habits.forEach { habit ->
                    Text(habit.Habit)
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
                onGoHome2 = { navController.navigate(Routes.calendaryScreen.routes) },
                onUserGo2 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}
