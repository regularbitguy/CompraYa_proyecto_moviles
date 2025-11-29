package com.example.prueba.ui.chats

data class ChatItem(
    val chatId: String = "",
    val usuarioId: String = "",
    var nombre: String = "",
    var fotoPerfil: String? = null,
    var ultimoMensaje: String = "",
    var hora: String = ""
)

