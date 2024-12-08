package ec.epn.edu

import java.util.*

fun main(args: Array<String>) {
    // INMUTABLES (No se RE ASIGNA "=")
    val inmutable: String = "Carlos";
    // inmutable = "Andrés" // Error!
    // MUTABLES
    var mutable: String = "Andrés"
    mutable = "Carlos" // Ok
    // VAL > VAR

    // Duck Typing
    val ejemploVariable = " Carlos López "
    ejemploVariable.trim()
    val edadEjemplo: Int = 25
    // ejemploVariable = edadEjemplo // Error!
    // Variables Primitivas
    val nombreEmpleado: String = "Carlos López"
    val salario: Double = 2.5
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true
    // Clases en Java
    val fechaNacimiento: Date = Date()

    // When (Switch)
    val estadoCivilWhen = "S"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito
    imprimirNombre("Maria Fernanda")

    calcularSueldo(15.00) // solo paramtro requerido
    calcularSueldo(15.00, 10.00, 25.00) // parametro requerido y sobreescribir parametros opcionales
    // Named parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(15.00, bonoEspecial = 30.00) // usando el parametro bonoEspecial en 2da posicion
    calcularSueldo(bonoEspecial = 30.00, sueldo = 15.00, tasa = 10.00)

    // CLASES USO:
    val sumaA = Suma(2, 3)
    val sumaB = Suma(null, 3)
    val sumaC = Suma(2, null)
    val sumaD = Suma(null, null)
    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(4))
    println(Suma.historialSumas)

    // Arreglos
    // Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(10, 20, 30)
    println(arregloEstatico)
    // Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        10, 20, 30, 40, 50, 60, 70, 80, 90, 100
    )
    println(arregloDinamico)
    arregloDinamico.add(110)
    arregloDinamico.add(120)
    println(arregloDinamico)

    // FOR EACH => Unit
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }
    arregloDinamico.forEach { println("Valor Actual (it): ${it}") }

    // MAP
    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 50.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 10 }
    println(respuestaMapDos)

    // Filter
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACincuenta: Boolean = valorActual > 50
            return@filter mayoresACincuenta
        }

    val respuestaFilterDos = arregloDinamico.filter { it <= 50 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 50)
        }
    println(respuestaAny) // True
    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 50)
        }
    println(respuestaAll) // False
}

fun imprimirNombre(nombre: String): Unit {
    fun otraFuncionAdentro() {
        println("Otra función adentro")
    }
    println("Nombre: $nombre")
    println("Nombre: ${nombre}")
    println("Nombre: ${nombre + nombre}")
    println("Nombre: ${nombre.uppercase()}")
    otraFuncionAdentro()
}

fun calcularSueldo(
    sueldo: Double,
    tasa: Double = 10.00,
    bonoEspecial: Double? = null
): Double {
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * bonoEspecial
    }
}

abstract class Numeros(
    protected val numeroUno: Int,
    protected val numeroDos: Int,
    parametroExtra: Int? = null
) {
    init {
        println("Inicializando")
    }
}

class Suma(
    unoParametro: Int,
    dosParametro: Int,
) : Numeros(unoParametro, dosParametro) {
    val descripcionPublica: String = "Acceso Público"
    val descripcionImplicita: String = "Acceso Implicito"
    init {
        println("Instancia creada")
    }
    constructor(uno: Int?, dos: Int) : this(if (uno == null) 0 else uno, dos)
    constructor(uno: Int, dos: Int?) : this(uno, if (dos == null) 0 else dos)
    constructor(uno: Int?, dos: Int?) : this(if (uno == null) 0 else uno, if (dos == null) 0 else dos)

    fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object {
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int = num * num
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma: Int) {
            historialSumas.add(valorTotalSuma)
        }
    }
}
