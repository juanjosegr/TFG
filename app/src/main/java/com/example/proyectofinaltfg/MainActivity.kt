package com.example.proyectofinaltfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.proyectofinaltfg.Navigation.NavManager
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.AddNoteVM.AddNoteVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiarioVM.DiaryScreenVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiaryUpdateVM.UpdateNoteVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsAddVM.AddHabitVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM.UpdateHabitVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM.HabitScreenVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.LoginRegisterVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.PreferenceManager
import com.example.proyectofinaltfg.ui.theme.ProyectoFinalTFGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val loginScreenVM: LoginRegisterVM by viewModels()
        val diaryScreenVM: DiaryScreenVM by viewModels()
        val addNoteVM: AddNoteVM by viewModels()
        val updateNoteVM: UpdateNoteVM by viewModels()
        val habitScreenVM: HabitScreenVM by viewModels()
        val addHabitVM: AddHabitVM by viewModels()
        val updateHabitVM: UpdateHabitVM by viewModels()

        super.onCreate(savedInstanceState)
        PreferenceManager.init(this)
        enableEdgeToEdge()
        setContent {
            window.navigationBarColor = getColor(R.color.black)
            ProyectoFinalTFGTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavManager(loginScreenVM, diaryScreenVM, addNoteVM, updateNoteVM,addHabitVM,updateHabitVM, habitScreenVM)

                }
            }
        }
    }
}

