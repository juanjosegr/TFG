package com.example.proyectofinaltfg.TFGAPP.ui.view.HabitsGroup.HabitsView

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsAddVM.AddHabitVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsUpdateVM.UpdateHabitVM
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.HabitsGruopVM.HabitsVM.HabitScreenVM
import com.example.proyectofinaltfg.framedetextos.inter
import com.example.proyectofinaltfg.ui.theme.LightGreen
import com.example.proyectofinaltfg.ui.theme.Red40

@Composable
fun ColumnasSeparadas(
    navController: NavController,
    habitScreenVM: HabitScreenVM,
    updateHabitVM: UpdateHabitVM,
    addHabitVM: AddHabitVM
) {
    val habits by habitScreenVM.habitsData.collectAsState()
    Log.d("Compose,ColumnasSeparadas", "Number of notes: ${habits.size}")
    if (habits.isNotEmpty()) {
        val rightColumnNotes = habits.filter { it.hecha_hacer == "por hacer" }
        val leftColumnNotes = habits.filter { it.hecha_hacer == "hecha" }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Left side
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(8.dp)

            ) {
                Column {
                    for (habit in rightColumnNotes) {
                        CustomTextBox(
                            backgroundColor = Red40,
                            text = habit.habit,
                            textColor = Color.Black,
                            navController = navController,
                            //updateHabitVM = updateHabitVM,
                            idDoc = habit.idNote
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Right side
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(8.dp)

            ) {
                Column {
                    for (habit in leftColumnNotes) {
                        CustomTextBox(
                            backgroundColor = LightGreen,
                            text = habit.habit,
                            textColor = Color.Black,
                            navController = navController,
                            //updateHabitVM = updateHabitVM,
                            idDoc = habit.idNote
                        )
                    }
                }
            }
        }
    } else {
        Text("No hay notas disponibles.")
    }
}

@Composable
fun CustomTextBox(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    navController: NavController,
    //updateHabitVM: updateHabitVM,
    idDoc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = {

                }),
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
                Text(
                    text,
                    color = textColor,
                    fontSize = 16.0.sp,
                    fontFamily = inter,
                    fontWeight = FontWeight(700.0.toInt())
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

}