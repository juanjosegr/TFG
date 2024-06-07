package com.example.proyectofinaltfg.TFGAPP.ui.view.ApiView

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.proyectofinaltfg.Navigation.Routes.Routes
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.ApiVM.ApiVM
import com.example.proyectofinaltfg.arribaaleatorio.ArribaAleatorio
import com.example.proyectofinaltfg.dislike.Dislike
import com.example.proyectofinaltfg.menuabajoaleatorio.MenuAbajoAleatorio
import com.example.proyectofinaltfg.reload.Reload
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

/**
 * Composable que representa la pantalla de "Me gusta" de la API, donde se muestran imágenes de gatos y frases favoritas.
 *
 * @param navController El NavController para la navegación entre pantallas.
 * @param apiVM El ViewModel que gestiona las operaciones relacionadas con la API.
 */
@Composable
fun ApiLikeScreen(
    navController: NavController,
    apiVM: ApiVM
) {
    // Recolecta el estado de los gatos y frases marcados como favoritos
    val likedCatsAndPhrases by apiVM.getLikedCatsAndPhrases().collectAsState(emptyList())
    // Estado para el índice actual
    val currentIndex = remember { mutableStateOf(0) }
    // Configuración del traductor
    val options = TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH).setTargetLanguage(
        TranslateLanguage.SPANISH).build()
    val englishSpanishTranslator = Translation.getClient(options)
    val conditions = DownloadConditions.Builder().requireWifi().build()
    var translatedText by remember { mutableStateOf<String?>(null) }
    DisposableEffect(Unit) {
        englishSpanishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {

            }
            .addOnFailureListener { exception ->
                Log.e("MLKit", "Error descarga: ${exception.message}")
            }
        onDispose {
            englishSpanishTranslator.close()
        }
    }

    fun translateText(text: String) {
        englishSpanishTranslator.translate(text)
            .addOnSuccessListener { translation ->
                translatedText = translation
            }
            .addOnFailureListener { exception ->
                Log.e("MLKit", "Error traduccion texto: ${exception.message}")
            }
    }
    // Maneja la acción del botón de retroceso
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
                // Verifica si hay gatos y frases guardados como favoritos
                if (likedCatsAndPhrases.isNotEmpty()) {
                    // Obtiene el gato y la frase actuales
                    val (currentCat, currentPhrase) = likedCatsAndPhrases[currentIndex.value]
                    // Muestra la imagen del gato
                    Image(
                        painter = rememberImagePainter(data = currentCat.url),
                        contentDescription = "Cat Image",
                        modifier = Modifier
                            .size(400.dp)
                            .padding(16.dp)
                            .border(width = 4.dp, color = Color.Black)
                            .background(Color.LightGray)
                    )

                    // Muestra la cita y la traducción
                    LaunchedEffect(currentPhrase.quote) {
                        translateText(currentPhrase.quote)
                    }

                    var showOriginalText by remember { mutableStateOf(true) }

                    val originalText = currentPhrase.quote
                    val displayText = if (showOriginalText) originalText else translatedText ?: originalText

                    val displayTextModifier = Modifier.clickable {
                        showOriginalText = !showOriginalText
                    }

                    Text(
                        text = displayText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(displayTextModifier)
                    )

                    // Muestra el botón "Traducir texto" si hay una traducción disponible
                    if (translatedText != null) {
                        Text(
                            text = if (showOriginalText) "Traducir texto" else "Mostrar original",
                            textAlign = TextAlign.Center,
                            color = Color.Blue,
                            modifier = Modifier.clickable {
                                showOriginalText = !showOriginalText
                            }
                        )
                    }

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = currentPhrase.author,
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                } else {
                    Text("No hay gatos guardados como favoritos.")
                }

                Row {
                    Dislike(ondislike = {
                        apiVM.showDeleteConfirmationDialog()
                    })
                    Spacer(modifier = Modifier.width(70.dp))
                    Reload(
                        onReload = {
                            if (likedCatsAndPhrases.isNotEmpty()) {
                                currentIndex.value =
                                    (currentIndex.value + 1) % likedCatsAndPhrases.size
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

    // Diálogo de confirmación para eliminar un gato
    if (apiVM.deleteConfirmationDialog) {
        AlertDialog(
            onDismissRequest = {
                apiVM.showDeleteConfirmationDialog()
            },
            title = { Text("¿Estás seguro que deseas borrar este gato?") },
            confirmButton = {
                Button(
                    onClick = {
                        val (currentCat, _) = likedCatsAndPhrases[currentIndex.value]
                        apiVM.deleteCatFromFirestore(currentCat)
                        apiVM.LikeShowAlertScreen()
                        apiVM.showDeleteConfirmationDialog()
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        apiVM.showDeleteConfirmationDialog()
                    }
                ) {
                    Text("No")
                }
            }
        )
    }

    // Alerta de éxito al eliminar un gato
    if (apiVM.showAlertLike) {
        AlertDialog(
            onDismissRequest = {
                apiVM.LikeShowAlertScreen()
            },
            title = { Text("Gato borrado") },
            text = { Text("El gato ha sido borrado.") },
            confirmButton = {
                Button(
                    onClick = {
                        apiVM.LikeShowAlertScreen()
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
