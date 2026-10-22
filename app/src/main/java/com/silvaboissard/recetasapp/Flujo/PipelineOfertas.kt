package com.silvaboissard.recetasapp.flujo

import java.io.IOException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.transform

data class OfertaReceta(
    val nombreReceta: String,
    val precio: Double,
    val disponible: Boolean,
)

// Estados que la UI va a observar
sealed interface EstadoOferta {
    object Cargando : EstadoOferta
    data class Datos(val oferta: OfertaReceta) : EstadoOferta
    data class Aviso(val mensaje: String) : EstadoOferta
    data class Error(val mensaje: String) : EstadoOferta
}

// Dos fuentes simuladas. Son flows "fríos": no hacen nada hasta que alguien
// los colecta, y cada nueva colección ejecuta el bloque desde cero.
class FuentesOfertas(private val fallarEnPrimerIntento: Boolean = false) {
    private var intentosPrecios = 0

    // Fuente 1: precios. Cada 500 ms emite un precio (el 0.0 es un dato inválido
    // a propósito, para que el filter tenga algo que descartar).
    fun precios(): Flow<Double> = flow {
        intentosPrecios++
        if (fallarEnPrimerIntento && intentosPrecios == 1) {
            throw IOException("Servicio de precios no disponible")
        }
        listOf(250.0, 0.0, 240.0, 260.0, 230.0).forEach {
            delay(5000)
            emit(it)
        }
    }

    // Fuente 2: disponibilidad. Cada 700 ms emite si hay stock o no.
    fun disponibilidad(): Flow<Boolean> = flow {
        listOf(true, true, false, true, false).forEach {
            delay(700)
            emit(it)
        }
    }
}

fun construirPipeline(fuentes: FuentesOfertas, nombreReceta: String): Flow<EstadoOferta> =
// COMBINE: cada vez que CUALQUIERA de las dos fuentes emite, se genera un
// nuevo par usando el último valor conocido de la otra. (zip, en cambio,
    // esperaría a que ambas emitan para formar cada par.)
    combine(fuentes.precios(), fuentes.disponibilidad()) { precio, disponible ->
        precio to disponible
    }
        // FILTER: descarta datos inválidos (precio 0).
        .filter { (precio, _) -> precio > 0.0 }
        // MAP: convierte el par en un objeto de dominio.
        .map { (precio, disponible) -> OfertaReceta(nombreReceta, precio, disponible) }
        // TRANSFORM: puede emitir 0, 1 o varios valores por cada entrada.
        // Aquí emitimos siempre los datos y, si el precio es bajo, un aviso extra.
        .transform { oferta ->
            emit(EstadoOferta.Datos(oferta))
            if (oferta.precio < 240.0) {
                emit(EstadoOferta.Aviso("¡Precio bajo en ${oferta.nombreReceta}!"))
            }
        }
        // RETRY: si falla por IOException, reintenta hasta 2 veces
        // (re-colecta todo lo de arriba desde cero).
        .retry(retries = 2) { causa -> causa is IOException }
        // CATCH: si el error persiste, lo convierte en un estado en vez de
        // dejar que la excepción reviente la app. Va DESPUÉS de retry.
        .catch { emit(EstadoOferta.Error(it.message ?: "Error desconocido")) }
        // Al empezar a colectar, lo primero que ve la UI es Cargando.
        .onStart { emit(EstadoOferta.Cargando) }