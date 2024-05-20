package com.example.proyectofinaltfg.TFGAPP.ui.view.ApiView

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.ApiVM.ApiVM
import com.example.proyectofinaltfg.arribaaleatorio.ArribaAleatorio
import com.example.proyectofinaltfg.likereload.LikeReload
import com.example.proyectofinaltfg.menuabajovariant5.MenuAbajoVariant5

@Composable
fun ApiScreen(
    navController: NavController
) {
    val viewModel: ApiVM = viewModel()
    val cats by viewModel.catList
    val pharses by viewModel.pharsesList
    var showAlert by remember { mutableStateOf(false) }

    BackHandler {
        navController.navigate(Routes.principalMenuScreen.routes)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Box {
                ArribaAleatorio()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-80).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (cats.isNotEmpty() && pharses.isNotEmpty()) {
                    val cat = cats.first()
                    val phrase = pharses.random()

                    Image(
                        painter = rememberImagePainter(data = cat.url),
                        contentDescription = "Cat Image",
                        modifier = Modifier
                            .size(400.dp)
                            .padding(16.dp)
                            .border(width = 4.dp, color = Color.Black)
                            .background(Color.LightGray)
                    )
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = phrase.quote,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = phrase.author,
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    LikeReload(
                        likeSave = {
                            viewModel.saveCatToFirestore(cat, phrase)
                            showAlert = true
                            viewModel.reloadCats()
                        },
                        reloadOn = { viewModel.reloadCats() }
                    )
                } else {
                    CircularProgressIndicator()
                }
            }
            if (showAlert) {
                AlertDialog(
                    onDismissRequest = {
                        showAlert = false
                    },
                    title = { Text("OK") },
                    text = { Text("Imagen y frase han sido guardada.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showAlert = false
                            }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = (-46).dp)
        ) {
            MenuAbajoVariant5(
                onHomeGo5 = { navController.navigate(Routes.principalMenuScreen.routes) },
                onLikeBtn = { navController.navigate(Routes.ApiLikeScreen.routes) },
                onUserGo5 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}