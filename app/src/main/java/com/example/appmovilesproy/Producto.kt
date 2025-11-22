package com.example.appmovilesproy
import com.google.firebase.firestore.DocumentId
data class Producto(
    @DocumentId
    var id: String = "",
    val titulo: String? = "",
    val descripcion: String? = "",
    val precio: Double? = null,
    val estado: String? = "",
    val imagenUrl: String? = "",
    val usuarioId: String? = "",
    val timestamp: Long? = null
)