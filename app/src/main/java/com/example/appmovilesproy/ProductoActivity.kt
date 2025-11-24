package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appmovilesproy.databinding.ActivityProductoBinding
import com.example.prueba.ui.chats.ChatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class ProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
        val precio = intent.getDoubleExtra("precio", 0.0)
        val imagenUrl = intent.getStringExtra("imagenUrl")

        // FIX: 1. Retrieve the vendedorId from the intent
        val vendedorId = intent.getStringExtra("vendedorId")

        binding.txtNombreProducto.text = nombre
        binding.txtDescripcion.text = descripcion
        binding.txtPrecio.text = "S/ $precio"

        Glide.with(this)
            .load(imagenUrl)
            .placeholder(R.drawable.img)
            .into(binding.imgProd)

        binding.btnContactar.setOnClickListener {
            if (!vendedorId.isNullOrEmpty()) {
                iniciarChatConVendedor(vendedorId)
            }
        }
    }

    private fun iniciarChatConVendedor(vendedorId: String) {

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        // Crear un ID único para el chat basándonos en ambos UIDs
        val chatId = if (currentUserId < vendedorId) {
            currentUserId + " _ " + vendedorId
        } else {
            vendedorId + " _ " + currentUserId
        }

        val chatRef = db.collection("chats").document(chatId)

        chatRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                abrirChat(chatId, vendedorId)
            } else {
                // Crear chat nuevo
                val chatData = hashMapOf(
                    "chatId" to chatId,
                    "participants" to listOf(currentUserId, vendedorId),
                    "lastMessage" to "",
                    "timestamp" to System.currentTimeMillis()
                )

                chatRef.set(chatData).addOnSuccessListener {
                    abrirChat(chatId, vendedorId)
                }
            }
        }
    }
    private fun abrirChat(chatId: String, vendedorId: String) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("vendedorId", vendedorId)
        startActivity(intent)
    }
}
