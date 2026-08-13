package com.silvaboissard.recetasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.silvaboissard.recetasapp.ui.navigation.AppPrincipal
import com.silvaboissard.recetasapp.ui.theme.RecetasAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecetasAppTheme {
                AppPrincipal()
            }
        }
    }
}