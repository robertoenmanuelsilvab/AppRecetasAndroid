package com.silvaboissard.recetasapp.lib

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ContenedorTest {

    @Test
    fun transformar_aplicaLaFuncionACadaElemento() {
        val resultado = contenedorDe(1, 2, 3).transformar { it * 2 }
        assertEquals(listOf(2, 4, 6), resultado.elementos)
    }

    @Test
    fun filtrar_conservaSoloLosQueCumplen() {
        val resultado = contenedorDe(1, 2, 3, 4).filtrar { it % 2 == 0 }
        assertEquals(listOf(2, 4), resultado.elementos)
    }

    @Test
    fun acumular_sumaConValorInicial() {
        val suma = contenedorDe(1, 2, 3).acumular(10) { acc, n -> acc + n }
        assertEquals(16, suma)
    }

    @Test
    fun reducir_devuelveNullSiEstaVacio() {
        val vacio = Contenedor<Int>(emptyList())
        assertNull(vacio.reducir { a, b -> a + b })
    }

    @Test
    fun reducir_combinaLosElementos() {
        assertEquals(6, contenedorDe(1, 2, 3).reducir { a, b -> a + b })
    }

    @Test
    fun mayorYMenor_funcionanConComparables() {
        val numeros = contenedorDe(5, 3, 9, 1)
        assertEquals(9, numeros.mayor())
        assertEquals(1, numeros.menor())
    }

    @Test
    fun ordenado_devuelveElementosEnOrdenAscendente() {
        assertEquals(listOf(1, 3, 5, 9), contenedorDe(5, 3, 9, 1).ordenado().elementos)
    }

    @Test
    fun filtrarPorTipo_separaStringsDeEnteros() {
        val mezcla = contenedorDe<Any>(1, "hola", 2.5, "mundo", 7)
        assertEquals(listOf("hola", "mundo"), mezcla.filtrarPorTipo<String>().elementos)
        assertEquals(listOf(1, 7), mezcla.filtrarPorTipo<Int>().elementos)
    }

    @Test
    fun combinar_unePosicionesDeAmbosContenedores() {
        val unido = contenedorDe(1, 2).combinar(contenedorDe(3))
        assertEquals(3, unido.tamano)
    }

    @Test
    fun filtrarCon_usaElValidador() {
        val soloPares = object : Validador<Int> {
            override fun validar(valor: Int) = valor % 2 == 0
        }
        assertTrue(contenedorDe(1, 2, 3, 4).filtrarCon(soloPares).elementos == listOf(2, 4))
    }

    @Test
    fun covarianza_contenedorDeStringSeUsaComoContenedorDeAny() {
        val strings: Contenedor<String> = contenedorDe("a", "b")
        val cualquiera: Contenedor<Any> = strings // compila gracias a "out T"
        assertEquals(2, cualquiera.tamano)
    }

    @Test
    fun describir_incluyeTituloYCantidad() {
        val texto = contenedorDe("a", "b").describir("Prueba")
        assertTrue(texto.contains("== Prueba =="))
        assertTrue(texto.contains("Elementos: 2"))
    }

    @Test
    fun resumenDelMayor_conElementos() {
        assertEquals("El mayor es 9", contenedorDe(5, 9, 1).resumenDelMayor())
    }

    @Test
    fun resumenDelMayor_contenedorVacio() {
        val vacio = Contenedor<Int>(emptyList())
        assertEquals("Contenedor vacio, no hay mayor", vacio.resumenDelMayor())
    }

    @Test
    fun filtrarConLog_registraElResultadoYDevuelveElFiltrado() {
        val mensajes = mutableListOf<String>()
        val resultado = contenedorDe(1, 2, 3, 4)
            .filtrarConLog({ it > 2 }, log = { mensajes.add(it) })

        assertEquals(listOf(3, 4), resultado.elementos)
        assertEquals(listOf("Filtrado: quedaron 2 de 4 elementos"), mensajes)
    }

}