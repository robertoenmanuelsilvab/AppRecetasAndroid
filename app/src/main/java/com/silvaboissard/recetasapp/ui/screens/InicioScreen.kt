package com.silvaboissard.recetasapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.silvaboissard.recetasapp.data.RecetasRepo
import com.silvaboissard.recetasapp.ui.navigation.Pantalla


@Composable
fun InicioScreen(navController: NavController) {

    val categorias = RecetasRepo.recetas
        .map { it.categoria }
        .distinct()

    Scaffold(

        // BOTÓN FLOTANTE PARA AGREGAR RECETA
        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate(Pantalla.AgregarReceta.ruta)
                }
            ) {
                Text(
                    text = "+",
                    fontSize = 24.sp
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TÍTULO
            Text(
                text = "¡Bienvenido a RecetasApp!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            // DESCRIPCIÓN
            Text(
                text = "Explora recetas por categoria o mira la lista completa",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 24.dp
                )
            )

            // TÍTULO DE CATEGORÍAS
            Text(
                text = "Categorias",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // CATEGORÍAS
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {

                items(categorias) { categoria ->

                    Card(
                        onClick = {
                            navController.navigate(
                                Pantalla.ListaPorCategoria.crearRuta(categoria)
                            )
                        },
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {

                        Text(
                            text = categoria,
                            modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical = 12.dp
                            ),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // ESPACIO
            Spacer(
                modifier = Modifier.weight(1f)
            )

            // BOTÓN VER TODAS
            Button(
                onClick = {
                    navController.navigate(Pantalla.Lista.ruta)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver todas las recetas")
            }
        }
    }
}