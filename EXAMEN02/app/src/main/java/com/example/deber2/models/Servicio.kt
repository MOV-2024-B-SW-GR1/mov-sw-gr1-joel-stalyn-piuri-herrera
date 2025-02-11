package com.example.deber2.models

data class Servicio(
    val id: String = 0.toString(), // ID autoincremental en la base de datos
    val nombre: String,
    val cantidadSuscripciones: String,
    val descripcion: String
)