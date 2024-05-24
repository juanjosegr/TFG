package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.LoginView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.proyectofinaltfg.TFGAPP.ui.view.GenericComponent.PasswordVisibleIcon
import com.example.proyectofinaltfg.TFGAPP.ui.view.GenericComponent.ShowAlert
import com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM.LoginRegisterVM
import com.example.proyectofinaltfg.grupologin.BtnLogin
import com.example.proyectofinaltfg.grupologin.Conectar
import com.example.proyectofinaltfg.grupologin.ConectarbtnLogin
import com.example.proyectofinaltfg.grupologin.Frame2
import com.example.proyectofinaltfg.grupologin.Frame3
import com.example.proyectofinaltfg.grupologin.NoEstasRegistrado
import com.example.proyectofinaltfg.grupologin.Rectangle6
import com.example.proyectofinaltfg.grupologin.Registrate
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerScope
/**
 * Componente composable que muestra el formulario de inicio de sesión y registro.
 *
 * @param modifier Modificador para personalizar la apariencia y el diseño del componente.
 * @param onBtnLogin Acción a realizar cuando se presiona el botón de inicio de sesión.
 * @param onRegistedTap Acción a realizar cuando se presiona el botón de registro.
 * @param loginScreenVM ViewModel para manejar la lógica de inicio de sesión y registro.
 * @param passwordVisible Estado mutable para controlar la visibilidad de la contraseña.
 * @param recordarUsuario Estado mutable para controlar si se debe recordar el usuario.
 */
@Composable
fun GrupoLoginNuevo(
    modifier: Modifier = Modifier,
    onBtnLogin: () -> Unit = {},
    onRegistedTap: () -> Unit = {},
    loginScreenVM: LoginRegisterVM,
    passwordVisible: MutableState<Boolean>,
    recordarUsuario: MutableState<Boolean> = remember { mutableStateOf(false) }

) {

    DisposableEffect(Unit) {
        onDispose {
            loginScreenVM.guardarPreferencias()
        }
    }

    // Transformación visual para ocultar o mostrar la contraseña
    val visualTranformaction = if (passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()

    TopeLevel(modifier = modifier) {
        Conectar()
        Frame2 {
            OutlinedTextField(
                value = loginScreenVM.email,
                onValueChange = { loginScreenVM.changeEmail(it) },
                modifier = Modifier.fillMaxSize(),
                label = { Text(text = "Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = ""
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(32.dp)
            )
        }
        Frame3 {
            OutlinedTextField(
                value = loginScreenVM.password,
                onValueChange = { loginScreenVM.changePasww(it) },
                modifier = Modifier.fillMaxSize(),
                label = { Text(text = "Contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = ""
                    )
                },
                visualTransformation = visualTranformaction,
                trailingIcon = {
                    if (loginScreenVM.password.isNotBlank()) {
                        PasswordVisibleIcon(passwordVisible)
                    } else null
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(32.dp)
            )
        }
        BtnLogin(onBtnLogin = onBtnLogin) {
            Rectangle6(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
            ConectarbtnLogin(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = (-3.0246124267578125).dp,
                        y = 0.0000762939453125.dp
                    )
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().offset(x = -50.dp, y = -20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = loginScreenVM.recordarUsuario,
                onCheckedChange = { newValue ->
                    loginScreenVM.recordarUsuario = newValue
                    loginScreenVM.guardarPreferencias()
                }
            )
            Text(
                text = "Recordarme",
                style = MaterialTheme.typography.body1,

            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().offset(y = -30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
        NoEstasRegistrado()
        Registrate(onRegistedTap = onRegistedTap)}
    }
}


/**
 * Muestra un diálogo de alerta si showAlert es verdadero en el ViewModel.
 *
 * @param loginScreenVM ViewModel que maneja la lógica del diálogo de alerta.
 * @param text Texto a mostrar en el diálogo de alerta.
 * @param caso Caso que describe el propósito del diálogo de alerta.
 */
@Composable
fun LlamadaShowAler(loginScreenVM: LoginRegisterVM, text: String, caso: String) {
    if (loginScreenVM.showAlert) {
        ShowAlert(
            caso,
            text,
            "Acepart",
            onAcceptClick = { loginScreenVM.closedShowAlert() },
            OnDissmisClicl = { }
        )
    }
}

/**
 * Layout de nivel superior para organizar los componentes en una fila.
 *
 * @param modifier Modificador para personalizar la apariencia y el diseño del contenedor.
 * @param content Contenido que se colocará dentro del contenedor.
 */
@Composable
fun TopeLevel(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        itemSpacing = 24.0,
        clipToParent = false,
        content = content,
        modifier = modifier
            .fillMaxWidth(1.0f)
            .fillMaxHeight(1.0f)
    )
}