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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

/**
 * Composable que representa la pantalla de la API, donde se muestran imágenes de gatos y frases aleatorias.
 *
 * @param navController El NavController para la navegación entre pantallas.
 */
@Composable
fun ApiScreen(
    navController: NavController
) {
    // ViewModel para manejar las operaciones relacionadas con la API
    val viewModel: ApiVM = viewModel()
    // Obtiene la lista de gatos y frases del ViewModel
    val cats by viewModel.catList
    val pharses by viewModel.pharsesList
    var translatedText by remember { mutableStateOf<String?>(null) }

    BackHandler {
        navController.navigate(Routes.principalMenuScreen.routes)
    }

    // Configuración del traductor
    val options = TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH).setTargetLanguage(TranslateLanguage.SPANISH).build()
    val englishSpanishTranslator = Translation.getClient(options)
    val conditions = DownloadConditions.Builder().requireWifi().build()


    DisposableEffect(Unit) {
        englishSpanishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully
            }
            .addOnFailureListener { exception ->
                // Handle the error
                Log.e("MLKit", "Error downloading model: ${exception.message}")
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
                // Handle the error
                Log.e("MLKit", "Error translating text: ${exception.message}")
            }
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

                // Verifica si hay gatos y frases disponibles
                if (cats.isNotEmpty() && pharses.isNotEmpty()) {
                    val cat = cats.first()
                    val phrase = pharses.random()

                    // Muestra la imagen del gato
                    Image(
                        painter = rememberImagePainter(data = cat.url),
                        contentDescription = "Cat Image",
                        modifier = Modifier
                            .size(400.dp)
                            .padding(16.dp)
                            .border(width = 4.dp, color = Color.Black)
                            .background(Color.LightGray)
                    )
                    // Muestra la cita

                    LaunchedEffect(phrase.quote) {
                        translateText(phrase.quote)
                    }

                    // Muestra el texto original o traducido alternativamente
                    var showOriginalText by remember { mutableStateOf(true) }

                    val originalText = phrase.quote
                    val displayText = if (showOriginalText) originalText else translatedText ?: originalText

                    // Muestra el texto original o traducido alternativamente
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
                            text = phrase.author,
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    LikeReload(
                        likeSave = {
                            viewModel.saveCatToFirestore(cat, phrase)
                            viewModel.trueShowAlertScreen()
                            viewModel.reloadCats()
                        },
                        reloadOn = { viewModel.reloadCats() }
                    )
                } else {
                    // Muestra un indicador de carga si no hay datos disponibles
                    CircularProgressIndicator()
                }
            }
            // Diálogo de alerta al guardar imagen y frase
            if (viewModel.showAlertScreen) {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.trueShowAlertScreen()
                    },
                    title = { Text("OK") },
                    text = { Text("Imagen y frase han sido guardada.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.trueShowAlertScreen()
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