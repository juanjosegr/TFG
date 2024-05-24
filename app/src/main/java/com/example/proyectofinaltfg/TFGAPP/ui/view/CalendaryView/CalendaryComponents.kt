package com.example.proyectofinaltfg.TFGAPP.ui.view.CalendaryView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofinaltfg.framedetextos.inter

/**
 * Composable que muestra una caja personalizada para notas.
 *
 * @param title El título de la nota.
 * @param text El contenido de la nota.
 * @param backgroundColor El color de fondo de la caja.
 * @param titleColor El color del título de la nota.
 * @param textColor El color del texto de la nota.
 */
@Composable
fun CustomNotesBox(
    title: String,
    text: String,
    backgroundColor: Color,
    titleColor: Color,
    textColor: Color,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
        ) {
            Text(
                title,
                color = titleColor,
                fontSize = 16.sp,
                fontFamily = inter,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text,
                color = textColor,
                fontSize = 12.sp,
                fontFamily = inter,
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}
/**
 * Composable que muestra una caja personalizada para hábitos.
 *
 * @param text El texto del hábito.
 * @param backgroundColor El color de fondo de la caja.
 * @param textColor El color del texto del hábito.
 */
@Composable
fun CustomHabitsBox(
    text: String,
    backgroundColor: Color,
    textColor: Color,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
        ) {
            Text(
                text,
                color = textColor,
                fontSize = 12.sp,
                fontFamily = inter,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}
