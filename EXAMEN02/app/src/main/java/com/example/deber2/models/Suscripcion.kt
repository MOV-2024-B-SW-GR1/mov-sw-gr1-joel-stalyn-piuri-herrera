package com.example.deber2.models

data class Suscripcion(
    val id: Int = 0, // ID autoincremental
    val nombre: String,
    val duracion: Int, // Duración en meses
    val costo: Double,
    val servicioId: Int // Relacionado con el servicio
)