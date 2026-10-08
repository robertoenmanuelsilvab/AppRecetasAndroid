package com.silvaboissard.recetasapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.silvaboissard.recetasapp.ui.screens.AgregarRecetaScreen
import com.silvaboissard.recetasapp.ui.screens.DetalleScreen
import com.silvaboissard.recetasapp.ui.screens.InicioScreen
import com.silvaboissard.recetasapp.ui.screens.ListaScreen

// Rutas centralizadas de la app
sealed class Pantalla(val ruta: String) {
    object Inicio : Pantalla("inicio")

    // Ruta sin filtro: muestra todas las recetas
    object Lista : Pantalla("lista")

    // Ruta con filtro: requiere una categoria en el path
    object ListaPorCategoria : Pantalla("lista/{categoria}") {
        fun crearRuta(categoria: String) = "lista/$categoria"
    }

    object Detalle : Pantalla("detalle/{recetaId}") {
        fun crearRuta(id: Int) = "detalle/$id"
    }

    object AgregarReceta : Pantalla("agregar_receta")
}

// Titulo que se muestra en la TopAppBar segun la pantalla actual
private fun tituloParaRuta(ruta: String?): String = when {
    ruta == null -> "RecetasApp"
    ruta == Pantalla.Inicio.ruta -> "RecetasApp"
    ruta.startsWith("lista") -> "Recetas"
    ruta.startsWith("detalle") -> "Detalle de receta"
    ruta == Pantalla.AgregarReceta.ruta -> "Agregar Receta"
    else -> "RecetasApp"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPrincipal() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tituloParaRuta(rutaActual)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Pantalla.Inicio.ruta,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(Pantalla.Inicio.ruta) {
                InicioScreen(navController = navController)
            }
            // Lista SIN filtro
            composable(Pantalla.Lista.ruta) {
                ListaScreen(navController = navController, categoriaFiltro = null)
            }
            // Lista CON filtro de categoria
            composable(
                route = Pantalla.ListaPorCategoria.ruta,
                arguments = listOf(navArgument("categoria") { type = NavType.StringType }),
            ) { backStack ->
                val categoria = backStack.arguments?.getString("categoria")
                ListaScreen(navController = navController, categoriaFiltro = categoria)
            }
            composable(
                route = Pantalla.Detalle.ruta,
                arguments = listOf(navArgument("recetaId") { type = NavType.IntType }),
            ) { backStack ->
                val id = backStack.arguments?.getInt("recetaId") ?: 0
                DetalleScreen(recetaId = id, navController = navController)
            }
            composable(Pantalla.AgregarReceta.ruta) {
                AgregarRecetaScreen(navController = navController)
            }
        }
    }
}