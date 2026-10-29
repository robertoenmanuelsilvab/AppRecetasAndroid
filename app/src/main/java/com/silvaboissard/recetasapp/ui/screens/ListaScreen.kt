package com.silvaboissard.recetasapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.silvaboissard.recetasapp.ui.navigation.Pantalla
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.silvaboissard.recetasapp.di.AppContainer
import com.silvaboissard.recetasapp.domain.model.Receta
import com.silvaboissard.recetasapp.ui.viewmodel.ListaViewModel



private val favoritosSaver = listSaver<SnapshotStateList<Int>, Int>(
    save = { it.toList() },
    restore = { it.toMutableStateList() },
)

@Composable
fun ListaScreen(navController: NavController, categoriaFiltro: String? = null) {

    val favoritos = rememberSaveable(saver = favoritosSaver) { mutableStateListOf() }


    val viewModel: ListaViewModel = viewModel(
        factory = viewModelFactory {
            initializer { ListaViewModel(categoriaFiltro, AppContainer.obtenerRecetas) }
        },
    )
    val uiState by viewModel.uiState.collectAsState()
    val recetasMostradas = uiState.recetas

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(recetasMostradas) { receta ->
            ItemReceta(
                receta = receta,
                esFavorito = favoritos.contains(receta.id),
                onFavoritoClick = {
                    if (favoritos.contains(receta.id)) {
                        favoritos.remove(receta.id)
                    } else {
                        favoritos.add(receta.id)
                    }
                },
                onClick = {
                    navController.navigate(Pantalla.Detalle.crearRuta(receta.id))
                },
            )
        }
    }
}

@Composable
fun ItemReceta(
    receta: Receta,
    esFavorito: Boolean,
    onFavoritoClick: () -> Unit,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = receta.categoria,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = receta.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "${receta.tiempoPrepMinutos} min",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = onFavoritoClick) {
                Icon(
                    imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}