package com.example.proyectofinaltfg.TFGAPP.ui.view.PrincipalView

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.proyectofinaltfg.agrupados.Decision
import com.example.proyectofinaltfg.agrupados.GoogleCalendar
import com.example.proyectofinaltfg.agrupados.GroupAleatory
import com.example.proyectofinaltfg.agrupados.GroupCalendary
import com.example.proyectofinaltfg.agrupados.GroupDiary
import com.example.proyectofinaltfg.agrupados.GroupHabits
import com.example.proyectofinaltfg.agrupados.GroupTraining
import com.example.proyectofinaltfg.agrupados.Journal
import com.example.proyectofinaltfg.agrupados.NoCelery
import com.example.proyectofinaltfg.agrupados.Rectangle10
import com.example.proyectofinaltfg.agrupados.Rectangle11
import com.example.proyectofinaltfg.agrupados.Rectangle13
import com.example.proyectofinaltfg.agrupados.Rectangle14
import com.example.proyectofinaltfg.agrupados.Rectangle15
import com.example.proyectofinaltfg.agrupados.TopLevel
import com.example.proyectofinaltfg.agrupados.Weightlifting
import com.example.proyectofinaltfg.menuabajovariant2.DogHouse
import com.example.proyectofinaltfg.menuabajovariant2.Plus
import com.example.proyectofinaltfg.menuabajovariant2.User
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope

/**
 * Muestra un grupo de elementos en una disposición de columnas.
 *
 * @param modifier Modificador para aplicar a la disposición de columnas.
 * @param onGroupAleatory Acción a realizar cuando se selecciona el grupo "Aleatorio".
 * @param onGroupTraining Acción a realizar cuando se selecciona el grupo "Entrenamiento".
 * @param onGroupCalendary Acción a realizar cuando se selecciona el grupo "Calendario".
 * @param onGroupHabits Acción a realizar cuando se selecciona el grupo "Hábitos".
 * @param onGroupDiary Acción a realizar cuando se selecciona el grupo "Diario".
 */
@Composable
fun Agrupadoss(
    modifier: Modifier = Modifier,
    onGroupAleatory: () -> Unit = {},
    onGroupTraining: () -> Unit = {},
    onGroupCalendary: () -> Unit = {},
    onGroupHabits: () -> Unit = {},
    onGroupDiary: () -> Unit = {}
) {
    TopLevel(modifier = modifier) {
        GroupAleatory(
            onGroupAleatory = onGroupAleatory,
            modifier = Modifier.boxAlign(
                alignment = Alignment.Center,
                offset = DpOffset(
                    x = 103.0.dp,
                    y = 71.0.dp
                )
            )
        ) {
            Rectangle13(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
            Decision(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
        }
        GroupTraining(
            onGroupTraining = onGroupTraining,
            modifier = Modifier.boxAlign(
                alignment = Alignment.Center,
                offset = DpOffset(
                    x = -103.0.dp,
                    y = 71.0.dp
                )
            )
        ) {
            Rectangle14(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
            Weightlifting(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -4.0.dp,
                        y = 0.0.dp
                    )
                )
            )
        }
        GroupCalendary(
            onGroupCalendary = onGroupCalendary,
            modifier = Modifier.boxAlign(
                alignment = Alignment.Center,
                offset = DpOffset(
                    x = 0.0.dp,
                    y = -85.0.dp
                )
            )
        ) {
            Rectangle15(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
            GoogleCalendar(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
        }
        GroupHabits(
            onGroupHabits = onGroupHabits,
            modifier = Modifier.boxAlign(
                alignment = Alignment.Center,
                offset = DpOffset(
                    x = 103.0.dp,
                    y = -251.0.dp
                )
            )
        ) {
            Rectangle11(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
            NoCelery(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
        }
        GroupDiary(
            onGroupDiary = onGroupDiary,
            modifier = Modifier.boxAlign(
                alignment = Alignment.Center,
                offset = DpOffset(
                    x = -103.0.dp,
                    y = -251.0.dp
                )
            )
        ) {
            Rectangle10(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
            Journal(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
        }
    }
}

/**
 * Muestra el menú inferior con variantes.
 *
 * @param modifier Modificador para aplicar al menú inferior.
 * @param onGoHome2 Acción a realizar cuando se selecciona la opción "Inicio".
 * @param onUserGo2 Acción a realizar cuando se selecciona la opción "Usuario".
 */
@Composable
fun MenuAbajoaVariant2(
    modifier: Modifier = Modifier,
    onGoHome2: () -> Unit = {},
    onUserGo2: () -> Unit = {}
) {
    TopeLevel(modifier = modifier) {
        DogHouse(onGoHome2 = onGoHome2)
        Plus()
        User(onUserGo2 = onUserGo2)
    }
}
/**
 * Nivel superior de la agrupación de elementos.
 *
 * @param modifier Modificador para aplicar al nivel superior.
 * @param content Contenido de la agrupación.
 */
@Composable
fun TopeLevel(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 50,
            green = 48,
            blue = 48
        ),
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 107.0,
        clipToParent = false,
        content = content,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(1.0f)
    )
}
