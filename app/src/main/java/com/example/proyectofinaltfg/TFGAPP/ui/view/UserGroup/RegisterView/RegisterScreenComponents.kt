package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.RegisterView

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.proyectofinaltfg.gruporegistro.*

/**
 * Grupo de componentes para el registro de un nuevo usuario.
 *
 * @param modifier Modificador para personalizar la apariencia y el diseño del grupo.
 * @param onBtnAcept Callback para manejar la acción de aceptar (registro) del usuario.
 * @param loginScreenVM ViewModel que maneja la lógica de inicio de sesión y registro.
 * @param passwordVisible Estado para controlar la visibilidad de la contraseña.
 */
@Composable
fun GrupoRegistroNuevo(
    modifier: Modifier = Modifier,
    onBtnAcept: () -> Unit = {},
    loginScreenVM: LoginRegisterVM,
    passwordVisible: MutableState<Boolean>
) {
    val visualTranformaction = if (passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()
    TopLevel(modifier = modifier) {
        Regristro()
        Frame2 {
            OutlinedTextField(
                value = loginScreenVM.userName,
                onValueChange = { loginScreenVM.chaneUserName(it) },
                modifier = Modifier.fillMaxSize(),
                label = { Text(text = "Nombre usuario") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = ""
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(32.dp)
            )
        }
        Frame3 {
            OutlinedTextField(
                value = loginScreenVM.email,
                onValueChange = { loginScreenVM.changeEmail(it) },
                modifier = Modifier.fillMaxSize(),
                label = { Text(text = "Email") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(32.dp)
            )
        }
        Frame4 {
            OutlinedTextField(
                value = loginScreenVM.password,
                onValueChange = { loginScreenVM.changePasww(it) },
                modifier = Modifier.fillMaxSize(),
                label = { Text(text = "Contraseña") },
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "") },
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
        BtnRgt(onBtnAcept = onBtnAcept) {
            Rectangle6(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
            Rgt(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -3.0247039794921875.dp,
                        y = 0.0000762939453125.dp
                    )
                )
            )
        }
    }
}

/**
 * Llamada para mostrar un diálogo de alerta en la pantalla de registro.
 *
 * @param loginScreenVM ViewModel que maneja la lógica de inicio de sesión y registro.
 * @param text Texto del mensaje de alerta.
 * @param caso Caso o tipo de alerta.
 */
@Composable
fun LlamadaShowAlert(loginScreenVM: LoginRegisterVM, text: String, caso: String) {
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