package com.example.prueba.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmovilesproy.R

class Chat(val nombre: String, val ultimoMensaje: String, val hora: String, val imagenResId: Int)

class ChatsAdapter(private val listaChats: List<Chat>) : RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgUsuario: ImageView = view.findViewById(R.id.imgUsuario)
        val tvNombreUsuario: TextView = view.findViewById(R.id.tvNombreUsuario)
        val tvUltimoMensaje: TextView = view.findViewById(R.id.tvUltimoMensaje)
        val tvHora: TextView = view.findViewById(R.id.tvHora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = listaChats[position]
        holder.tvNombreUsuario.text = chat.nombre
        holder.tvUltimoMensaje.text = chat.ultimoMensaje
        holder.tvHora.text = chat.hora
        holder.imgUsuario.setImageResource(chat.imagenResId)
    }

    override fun getItemCount() = listaChats.size
}
