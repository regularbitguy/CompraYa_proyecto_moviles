package com.example.appmovilesproy.model

data class Producto(
    val titulo: String? = "",
    val descripcion: String? = "",
    val precio: Double? = null,
    val estado: String? = "",
    val imagenUrl: String? = "",
    val usuarioId: String? = "",
    val timestamp: Long? = null
)