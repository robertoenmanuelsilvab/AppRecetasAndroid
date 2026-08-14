package com.silvaboissard.recetasapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.silvaboissard.recetasapp.data.recetasDeMuestra

@Composable
fun DetalleScreen(recetaId: Int, navController: NavController) {
    val receta = recetasDeMuestra.find { it.id == recetaId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        if (receta == null) {
            Text("Receta no encontrada")
        } else {
            Text(
                text = receta.categoria,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = receta.nombre,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Tiempo de preparación: ${receta.tiempoPrepMinutos} min",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
            )

            Text(
                text = "Ingredientes",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    receta.ingredientes.forEach { ingrediente ->
                        Text("• $ingrediente", modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }

            Text(
                text = "Pasos",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            )


            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(receta.pasos) { paso ->
                    val numero = receta.pasos.indexOf(paso) + 1
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "$numero. $paso",
                            modifier = Modifier.padding(12.dp),
                        )
                    }
                }
            }
        }

        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            Text("Regresar")
        }
    }
}