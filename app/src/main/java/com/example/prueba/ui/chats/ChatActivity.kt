package com.example.prueba.ui.chats

import MensajeAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appmovilesproy.Mensaje
import com.example.appmovilesproy.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private lateinit var chatId: String
    private lateinit var vendedorId: String
    private lateinit var adapter: MensajeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos enviados desde ProductoActivity
        chatId = intent.getStringExtra("chatId") ?: ""
        vendedorId = intent.getStringExtra("vendedorId") ?: ""
        cargarDatosVendedor()

        if (chatId.isEmpty() || vendedorId.isEmpty()) {
            Toast.makeText(this, "Error: faltan datos del chat.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupRecyclerView()
        escucharMensajes()
        setupEnviarMensaje()
    }

    private fun setupRecyclerView() {
        adapter = MensajeAdapter(auth.currentUser?.uid ?: "")
        binding.recyclerMensajes.layoutManager = LinearLayoutManager(this)
        binding.recyclerMensajes.adapter = adapter
    }

    private fun escucharMensajes() {
        db.collection("chats")
            .document(chatId)
            .collection("mensajes")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) return@addSnapshotListener

                val mensajes = value?.documents?.map { doc ->
                    Mensaje(
                        contenido = doc.getString("contenido") ?: "",
                        autorId = doc.getString("autorId") ?: "",
                        timestamp = doc.getLong("timestamp") ?: 0
                    )
                } ?: emptyList()

                adapter.submitList(mensajes)
                binding.recyclerMensajes.scrollToPosition(mensajes.size - 1)
            }
    }
    private fun cargarDatosVendedor() {
        db.collection("usuarios")
            .document(vendedorId)
            .get()
            .addOnSuccessListener { doc ->
                binding.txtNombreVendedor.text = doc.getString("nombre") ?: "Vendedor"

                val fotoUrl = doc.getString("fotoPerfil")
                if (!fotoUrl.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(fotoUrl)
                        .into(binding.imgVendedor)
                }
            }
    }


    private fun setupEnviarMensaje() {
        binding.btnEnviar.setOnClickListener {
            val texto = binding.etMensaje.text.toString().trim()
            if (texto.isEmpty()) return@setOnClickListener

            val mensaje = mapOf(
                "contenido" to texto,
                "autorId" to (auth.currentUser?.uid ?: ""),
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("chats")
                .document(chatId)
                .collection("mensajes")
                .add(mensaje)

            binding.etMensaje.setText("")
        }
    }
}
