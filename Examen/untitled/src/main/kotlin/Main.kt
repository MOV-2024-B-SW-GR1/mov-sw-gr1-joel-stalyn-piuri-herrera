import java.io.File
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class Suscripcion(
    val id: Int,
    var nombreCliente: String,
    var fechaInicio: String,
    var tipoServicio: String,
    var estado: Boolean,
    var precioMensual: Double,
    val servicios: MutableList<Servicio> = mutableListOf()
)

@Serializable
data class Servicio(
    val id: Int,
    var nombreServicio: String,
    var descripcion: String,
    var duracionMeses: Int,
    var precioTotal: Double,
    var fechaActivacion: String
)

fun main() {
    val suscripciones = mutableListOf<Suscripcion>()
    val servicios = mutableListOf<Servicio>()

    // Crear archivos si no existen y cargar datos
    inicializarArchivo("suscripciones.txt")
    inicializarArchivo("servicios.txt")
    cargarDatos(suscripciones, "suscripciones.txt")
    cargarDatos(servicios, "servicios.txt")

    while (true) {
        println("\nMenú Principal:")
        println("1. Crear Suscripción")
        println("2. Leer Suscripciones")
        println("3. Actualizar Suscripción")
        println("4. Eliminar Suscripción")
        println("5. Salir")
        print("Seleccione una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> crearSuscripcion(suscripciones, servicios)
            2 -> leerSuscripciones(suscripciones)
            3 -> actualizarSuscripcion(suscripciones)
            4 -> eliminarSuscripcion(suscripciones, servicios)
            5 -> {
                guardarDatos(suscripciones, "suscripciones.txt")
                guardarDatos(servicios, "servicios.txt")
                println("Datos guardados. Saliendo del programa.")
                break
            }
            else -> println("Opción inválida. Intente nuevamente.")
        }
    }
}

fun inicializarArchivo(archivo: String) {
    val file = File(archivo)
    if (!file.exists()) {
        file.createNewFile()
        println("Archivo creado: $archivo")
    }
}

fun crearSuscripcion(suscripciones: MutableList<Suscripcion>, servicios: MutableList<Servicio>) {
    println("Ingrese el nombre del cliente:")
    val nombreCliente = readLine()!!

    println("Ingrese la fecha de inicio (YYYY-MM-DD):")
    val fechaInicio = readLine()!!

    println("Ingrese el tipo de servicio:")
    val tipoServicio = readLine()!!

    println("Estado de la suscripción (true = activa, false = inactiva):")
    val estado = readLine()!!.toBoolean()

    println("Ingrese el precio mensual:")
    val precioMensual = readLine()!!.toDouble()

    val nuevaSuscripcion = Suscripcion(
        id = if (suscripciones.isEmpty()) 1 else suscripciones.maxOf { it.id } + 1,
        nombreCliente = nombreCliente,
        fechaInicio = fechaInicio,
        tipoServicio = tipoServicio,
        estado = estado,
        precioMensual = precioMensual
    )

    println("¿Cuántos servicios desea agregar?")
    val cantidadServicios = readLine()!!.toInt()

    for (i in 1..cantidadServicios) {
        println("Ingrese el nombre del servicio $i:")
        val nombreServicio = readLine()!!

        println("Ingrese la descripción del servicio $i:")
        val descripcion = readLine()!!

        println("Ingrese la duración en meses del servicio $i:")
        val duracionMeses = readLine()!!.toInt()

        println("Ingrese el precio total del servicio $i:")
        val precioTotal = readLine()!!.toDouble()

        println("Ingrese la fecha de activación (YYYY-MM-DD):")
        val fechaActivacion = readLine()!!

        val nuevoServicio = Servicio(
            id = if (servicios.isEmpty()) 1 else servicios.maxOf { it.id } + 1,
            nombreServicio = nombreServicio,
            descripcion = descripcion,
            duracionMeses = duracionMeses,
            precioTotal = precioTotal,
            fechaActivacion = fechaActivacion
        )
        nuevaSuscripcion.servicios.add(nuevoServicio)
        servicios.add(nuevoServicio)
    }

    suscripciones.add(nuevaSuscripcion)
    guardarDatos(suscripciones, "suscripciones.txt")
    guardarDatos(servicios, "servicios.txt")
    println("Suscripción creada con éxito.")
}

fun leerSuscripciones(suscripciones: List<Suscripcion>) {
    if (suscripciones.isEmpty()) {
        println("No hay suscripciones registradas.")
        return
    }

    suscripciones.forEach { suscripcion ->
        println("\nSuscripción ID: ${suscripcion.id}")
        println("Cliente: ${suscripcion.nombreCliente}")
        println("Fecha de inicio: ${suscripcion.fechaInicio}")
        println("Tipo de servicio: ${suscripcion.tipoServicio}")
        println("Estado: ${if (suscripcion.estado) "Activa" else "Inactiva"}")
        println("Precio mensual: ${suscripcion.precioMensual}")
        println("Servicios:")
        suscripcion.servicios.forEach { servicio ->
            println("  - Servicio ID: ${servicio.id}")
            println("    Nombre: ${servicio.nombreServicio}")
            println("    Descripción: ${servicio.descripcion}")
            println("    Duración: ${servicio.duracionMeses} meses")
            println("    Precio total: ${servicio.precioTotal}")
            println("    Fecha de activación: ${servicio.fechaActivacion}")
        }
    }
}

fun actualizarSuscripcion(suscripciones: MutableList<Suscripcion>) {
    println("Ingrese el ID de la suscripción a actualizar:")
    val id = readLine()!!.toInt()

    val suscripcion = suscripciones.find { it.id == id }
    if (suscripcion != null) {
        println("Ingrese el nuevo nombre del cliente (${suscripcion.nombreCliente}):")
        suscripcion.nombreCliente = readLine()!!

        println("Ingrese la nueva fecha de inicio (${suscripcion.fechaInicio}):")
        suscripcion.fechaInicio = readLine()!!

        println("Ingrese el nuevo tipo de servicio (${suscripcion.tipoServicio}):")
        suscripcion.tipoServicio = readLine()!!

        println("Ingrese el nuevo estado (${if (suscripcion.estado) "Activa" else "Inactiva"}):")
        suscripcion.estado = readLine()!!.toBoolean()

        println("Ingrese el nuevo precio mensual (${suscripcion.precioMensual}):")
        suscripcion.precioMensual = readLine()!!.toDouble()

        guardarDatos(suscripciones, "suscripciones.txt")
        println("Suscripción actualizada con éxito.")
    } else {
        println("Suscripción no encontrada.")
    }
}

fun eliminarSuscripcion(suscripciones: MutableList<Suscripcion>, servicios: MutableList<Servicio>) {
    println("Ingrese el ID de la suscripción a eliminar:")
    val id = readLine()!!.toInt()

    val suscripcion = suscripciones.find { it.id == id }
    if (suscripcion != null) {
        servicios.removeAll(suscripcion.servicios)
        suscripciones.remove(suscripcion)
        guardarDatos(suscripciones, "suscripciones.txt")
        guardarDatos(servicios, "servicios.txt")
        println("Suscripción eliminada con éxito.")
    } else {
        println("Suscripción no encontrada.")
    }
}

inline fun <reified T> cargarDatos(lista: MutableList<T>, archivo: String) {
    val file = File(archivo)
    if (file.exists()) {
        val contenido = file.readText()
        if (contenido.isNotBlank()) {
            val elementos = Json.decodeFromString<List<T>>(contenido)
            lista.addAll(elementos)
        }
    }
}

inline fun <reified T> guardarDatos(lista: List<T>, archivo: String) {
    val file = File(archivo)
    file.writeText(Json.encodeToString(lista))
}
