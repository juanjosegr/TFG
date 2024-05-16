package com.example.proyectofinaltfg.TFGAPP.ui.view.ApiView

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

                if (cats.isNotEmpty()) {
                    val cat = cats.first()
                    Image(
                        painter = rememberImagePainter(data = cat.url),
                        contentDescription = "Cat Image",
                        modifier = Modifier
                            .size(400.dp)
                            .padding(16.dp)
                            .border(width = 4.dp, color = Color.Black)
                            .background(Color.LightGray)
                    )
                    LikeReload(
                        likeSave = {
                            viewModel.saveCatToFirestore(cat)
                            showAlert = true
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
                    title = { Text("Imagen Guardada") },
                    text = { Text("La imagen del gato ha sido guardada.") },
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
                onLikeBtn = { navController.navigate(Routes.ApiLikeScreen.routes)},
                onUserGo5 = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }
}