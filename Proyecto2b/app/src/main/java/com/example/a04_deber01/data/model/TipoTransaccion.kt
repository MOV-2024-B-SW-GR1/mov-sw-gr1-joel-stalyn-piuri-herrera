package com.example.a04_deber01.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TipoTransaccion(
    val id: Int = 0,
    val nombre: String,
    val anioInicio: Int,
    val acreditada: Boolean,
    val creditosTotales: Int,
    val mensualidad: Double
)