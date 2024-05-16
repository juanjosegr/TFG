package com.example.proyectofinaltfg.TFGAPP.ui.view.ApiView

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.ApiVM.ApiVM
import com.example.proyectofinaltfg.arribaaleatorio.ArribaAleatorio
import com.example.proyectofinaltfg.dislike.Dislike
import com.example.proyectofinaltfg.menuabajoaleatorio.MenuAbajoAleatorio
import com.example.proyectofinaltfg.reload.Reload
import com.google.relay.compose.BoxScopeInstanceImpl.align

@Composable
fun ApiLikeScreen(
    navController: NavController,
    apiVM: ApiVM
) {
    val likedCats by apiVM.getLikedCats().collectAsState(initial = emptyList())
    val currentIndex = remember { mutableStateOf(0) }
    var showAlert by remember { mutableStateOf(false) }
    var deleteConfirmationDialog by remember { mutableStateOf(false) }
    fun showDeleteConfirmationDialog() {deleteConfirmationDialog = true}

    BackHandler {
        navController.navigate(Routes.principalMenuScreen.routes)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(bottom = 56.dp),
        ) {
            Box {
                ArribaAleatorio()
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-50).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (likedCats.isNotEmpty()) {
                    val currentCat = likedCats[currentIndex.value]
                    Image(
                        painter = rememberImagePainter(data = currentCat.url),
                        contentDescription = "Cat Image",
                        modifier = Modifier
                            .size(400.dp)
                            .padding(16.dp)
                            .border(width = 4.dp, color = Color.Black)
                            .background(Color.LightGray)
                    )
                } else {
                    Text("No hay gatos guardados como favoritos.")
                }

                Row {
                    Dislike(ondislike = {
                        showDeleteConfirmationDialog()
                    })
                    Spacer(modifier = Modifier.width(70.dp))
                    Reload(
                        onReload = {
                            if (likedCats.isNotEmpty()) {
                                currentIndex.value = (currentIndex.value + 1) % likedCats.size
                            }
                        }
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
            MenuAbajoAleatorio(
                onGoHomeAlet = { navController.navigate(Routes.principalMenuScreen.routes) },
                onLikeGo = { navController.navigate(Routes.apiScreen.routes) },
                onUserGo = { navController.navigate(Routes.userScren.routes) }
            )
        }
    }

    if (deleteConfirmationDialog) {
        AlertDialog(
            onDismissRequest = {
                deleteConfirmationDialog = false
            },
            title = { Text("¿Estás seguro que deseas borrar este gato?") },
            confirmButton = {
                Button(
                    onClick = {
                        val currentCat = likedCats[currentIndex.value]
                        apiVM.deleteCatFromFirestore(currentCat)
                        showAlert = true
                        deleteConfirmationDialog = false
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        deleteConfirmationDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = {
                showAlert = false
            },
            title = { Text("Gato borrado") },
            text = { Text("El gato ha sido borrado.") },
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
