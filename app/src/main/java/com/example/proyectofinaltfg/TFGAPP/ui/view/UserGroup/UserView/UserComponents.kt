package com.example.proyectofinaltfg.TFGAPP.ui.view.UserGroup.UserView

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.proyectofinaltfg.usuercamps.Apellidos
import com.example.proyectofinaltfg.usuercamps.ContraseA
import com.example.proyectofinaltfg.usuercamps.Emial
import com.example.proyectofinaltfg.usuercamps.Frame5
import com.example.proyectofinaltfg.usuercamps.Frame6
import com.example.proyectofinaltfg.usuercamps.Frame7
import com.example.proyectofinaltfg.usuercamps.Frame8
import com.example.proyectofinaltfg.usuercamps.Frame9
import com.example.proyectofinaltfg.usuercamps.Nombre
import com.example.proyectofinaltfg.usuercamps.RepiteContraseA
import com.example.proyectofinaltfg.usuercamps.TopLevel
import com.example.proyectofinaltfg.usuercamps.TopLevelSynth

@Composable
fun UsuerCampsMod(modifier: Modifier = Modifier) {
    TopLevel(modifier = modifier) {
        TopLevelSynth(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)) {
            Nombre()
            Frame5 {

            }
            Apellidos()
            Frame6 {

            }
            Emial()
            Frame7 {

            }
            ContraseA()
            Frame8 {

            }
            RepiteContraseA()
        }
        Frame9(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)) {}
    }
}