package com.example.myapplication

data class Suscripcion(
    val id: Long = 0, // id autoincremental
    val usuarioId: Long,
    val fechaInicio: String,
    val metodoPago: String,
    val servicioId: Long // Relación con el servicio
)

