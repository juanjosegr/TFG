package com.example.proyectofinaltfg.TFGAPP.ui.view.GenericComponent

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.DiaryPackageVM.DiaryUpdateVM.UpdateNoteVM
import com.example.proyectofinaltfg.framedetextos.inter

@Composable
fun ShowAlert(
    title: String,
    text: String,
    confirmText: String,
    onAcceptClick: () -> Unit,
    OnDissmisClicl: () -> Unit
) {

    val scroll = rememberScrollState(0)

    AlertDialog(onDismissRequest = { OnDissmisClicl() },
        title = { Text(text = title) },
        text = {
            Text(
                text = text,
                textAlign = TextAlign.Justify,
                modifier = Modifier.verticalScroll(scroll)
            )
        },
        confirmButton = {
            Button(onClick = { onAcceptClick() }) {
                Text(text = confirmText)
            }
        }
    )
}

@Composable
fun PasswordVisibleIcon(
    passwordVisible: MutableState<Boolean>
) {
    val image =
        if (passwordVisible.value) {
            Icons.Default.VisibilityOff
        } else {
            Icons.Default.Visibility
        }
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )

    }
}

@Composable
fun CustomTextBox(
    title: String,
    text: String,
    backgroundColor: Color,
    titleColor: Color,
    textColor: Color,
    navController: NavController,
    updateNoteVM: UpdateNoteVM,
    idDoc: String
) {
    Log.d("Compose,CustomTextBox", "CustomTextBox - Title: $title, Text: $text")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = {
                    updateNoteVM.allDateObtains(
                        title,
                        text,
                        backgroundColor,
                        idDoc
                    )
                    navController.navigate(Routes.diaryUpdateScren.routes)
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
                    title,
                    color = titleColor,
                    fontSize = 16.0.sp,
                    fontFamily = inter,
                    fontWeight = FontWeight(700.0.toInt())
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text,
                    color = textColor,
                    fontSize = 12.0.sp,
                    fontFamily = inter,
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}



