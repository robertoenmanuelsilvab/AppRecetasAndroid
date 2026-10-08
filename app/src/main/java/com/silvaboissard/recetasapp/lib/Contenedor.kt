package com.silvaboissard.recetasapp.lib
// Contenedor inmutable y generico.
// "out T" = covarianza: un Contenedor<Receta> puede usarse donde se espera
// un Contenedor<Any>, porque el contenedor solo PRODUCE elementos T
// (los entrega), nunca los recibe despues de creado.
class Contenedor<out T>(val elementos: List<T>) {

    val tamano: Int get() = elementos.size

    // map: transforma cada elemento y devuelve un contenedor de otro tipo
    fun <R> transformar(transformacion: (T) -> R): Contenedor<R> =
        Contenedor(elementos.map(transformacion))

    // filter: conserva solo los elementos que cumplen el predicado
    fun filtrar(predicado: (T) -> Boolean): Contenedor<T> =
        Contenedor(elementos.filter(predicado))

    // fold: acumula los elementos partiendo de un valor inicial de cualquier tipo
    fun <R> acumular(inicial: R, operacion: (R, T) -> R): R =
        elementos.fold(inicial, operacion)

    override fun toString(): String = "Contenedor$elementos"
}

// reduce: como fold pero sin valor inicial; devuelve null si el contenedor esta vacio.
// Es una funcion de EXTENSION y no un miembro porque (T, T) -> T pondria a T
// en posicion "in", y eso esta prohibido dentro de una clase declarada con "out T".
fun <T> Contenedor<T>.reducir(operacion: (T, T) -> T): T? =
    if (elementos.isEmpty()) null else elementos.reduce(operacion)

// Combina dos contenedores en uno nuevo (misma razon para ser extension)
fun <T> Contenedor<T>.combinar(otro: Contenedor<T>): Contenedor<T> =
    Contenedor(elementos + otro.elementos)

// Fabrica para crear contenedores de forma comoda: contenedorDe(1, 2, 3)
fun <T> contenedorDe(vararg items: T): Contenedor<T> = Contenedor(items.toList())

// ── TYPE BOUND ──────────────────────────────────────────────────────────
// "where T : Comparable<T>" restringe esta funcion a contenedores cuyos
// elementos se pueden comparar entre si (Int, String, Double...).
// Sin esta restriccion el compilador no sabria si dos T se pueden ordenar.
// Un Contenedor<Receta> NO puede llamar a mayor() porque Receta no es Comparable.
fun <T> Contenedor<T>.mayor(): T? where T : Comparable<T> =
    elementos.maxOrNull()

fun <T> Contenedor<T>.menor(): T? where T : Comparable<T> =
    elementos.minOrNull()

fun <T> Contenedor<T>.ordenado(): Contenedor<T> where T : Comparable<T> =
    Contenedor(elementos.sorted())

// ── INLINE + REIFIED ────────────────────────────────────────────────────
// Normalmente el tipo generico se "borra" en tiempo de ejecucion (type erasure),
// asi que no se puede escribir "elemento is R".
// "inline" copia el cuerpo de la funcion en el lugar donde se llama, y
// "reified" conserva el tipo real R, lo que permite filtrar por tipo.
// Se usa Contenedor<*> para aceptar un contenedor de cualquier tipo.
inline fun <reified R> Contenedor<*>.filtrarPorTipo(): Contenedor<R> =
    Contenedor(elementos.filterIsInstance<R>())

// ── CONTRAVARIANZA (in) ─────────────────────────────────────────────────
// "in T" = el validador solo CONSUME valores T, nunca los devuelve.
// Por eso un Validador<Any> puede usarse donde se pide un Validador<String>:
// si sabe validar cualquier cosa, tambien sabe validar un String.
interface Validador<in T> {
    fun validar(valor: T): Boolean
}

fun <T> Contenedor<T>.filtrarCon(validador: Validador<T>): Contenedor<T> =
    filtrar { validador.validar(it) }

// ── SCOPE FUNCTIONS ─────────────────────────────────────────────────────

// APPLY: configura un objeto y devuelve ese MISMO objeto.
// Lo elegi aqui porque quiero construir un StringBuilder, llenarlo linea por
// linea y recibirlo de vuelta para convertirlo a String al final. Dentro del
// bloque el receptor es "this", asi que llamo a appendLine() sin repetir el
// nombre del builder. Con "also" tendria que escribir "it.appendLine(...)".
fun <T> Contenedor<T>.describir(titulo: String): String =
    StringBuilder().apply {
        appendLine("== $titulo ==")
        appendLine("Elementos: $tamano")
        elementos.forEachIndexed { i, e -> appendLine("${i + 1}. $e") }
    }.toString()

// LET: ejecuta un bloque sobre un valor que puede ser null y devuelve el
// resultado del bloque. Lo elegi porque mayor() devuelve T? (null si el
// contenedor esta vacio) y "?.let" me da el idioma natural de Kotlin para
// "si hay valor, transformalo; si no, usa un texto por defecto" sin escribir
// un if (valor != null) explicito.
fun <T> Contenedor<T>.resumenDelMayor(): String where T : Comparable<T> =
    mayor()?.let { "El mayor es $it" } ?: "Contenedor vacio, no hay mayor"

// ALSO: ejecuta una accion secundaria (efecto lateral) y devuelve el objeto
// ORIGINAL sin modificarlo. Lo elegi porque quiero registrar un log del
// resultado sin romper la cadena de llamadas: el filtro sigue su camino y el
// "also" solo observa. Si usara "let" tendria que devolver el contenedor
// manualmente al final del bloque.
fun <T> Contenedor<T>.filtrarConLog(
    predicado: (T) -> Boolean,
    log: (String) -> Unit = ::println,
): Contenedor<T> =
    filtrar(predicado).also { log("Filtrado: quedaron ${it.tamano} de $tamano elementos") }