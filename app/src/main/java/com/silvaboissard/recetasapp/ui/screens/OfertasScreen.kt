package com.silvaboissard.recetasapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.silvaboissard.recetasapp.flujo.EstadoOferta
import com.silvaboissard.recetasapp.flujo.OfertasViewModel

@Composable
fun OfertasScreen(
    navController: NavController,
    viewModel: OfertasViewModel = viewModel(),
) {
    // collectAsState convierte el StateFlow en un State de Compose:
    // cada vez que el flujo emite, esta pantalla se recompone sola.
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // when exhaustivo sobre la sealed interface: si agregas un estado
        // nuevo y no lo manejas aqui, el compilador te avisa.
        when (val e = estado) {
            is EstadoOferta.Cargando -> {
                CircularProgressIndicator()
                Text("Consultando precios...", modifier = Modifier.padding(top = 16.dp))
            }
            is EstadoOferta.Datos -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(e.oferta.nombreReceta, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "Precio estimado: RD$ ${e.oferta.precio}",
                            modifier = Modifier.padding(top = 8.dp),
                        )
                        Text(
                            text = if (e.oferta.disponible) "Ingredientes disponibles" else "Sin stock",
                            color = if (e.oferta.disponible) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.error
                            },
                        )
                    }
                }
            }
            is EstadoOferta.Aviso -> Text(e.mensaje, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            is EstadoOferta.Error -> Text(e.mensaje, color = MaterialTheme.colorScheme.error)
        }

        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
        ) {
            Text("Regresar")
        }
    }
}