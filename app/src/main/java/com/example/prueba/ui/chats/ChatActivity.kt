package com.example.prueba.ui.chats

import MensajeAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appmovilesproy.Mensaje
import com.example.appmovilesproy.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.appmovilesproy.R

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var adapter: MensajeAdapter

    private var chatId: String = ""
    private var vendedorId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatId = intent.getStringExtra("chatId") ?: ""
        vendedorId = intent.getStringExtra("vendedorId") ?: ""

        Log.d("CHAT_TEST", "RECIBIDO vendedorId=$vendedorId chatId=$chatId")

        if (chatId.isEmpty()) {
            Toast.makeText(this, "Error al cargar chat", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupRecyclerView()
        cargarDatosOtroUsuario()
        escucharMensajes()
        setupEnviar()
    }

    private fun setupRecyclerView() {
        val miUid = auth.currentUser?.uid ?: ""
        adapter = MensajeAdapter(miUid)
        binding.recyclerMensajes.layoutManager = LinearLayoutManager(this)
        binding.recyclerMensajes.adapter = adapter
    }

    private fun cargarDatosOtroUsuario() {
        db.collection("usuarios").document(vendedorId).get().addOnSuccessListener { doc ->
            binding.txtNombreVendedor.text = doc.getString("nombre") ?: "Usuario"
            val foto = doc.getString("fotoPerfil")
            Glide.with(this).load(foto).placeholder(R.drawable.user).circleCrop().into(binding.imgVendedor)
        }
    }

    private fun escucharMensajes() {
        db.collection("chats").document(chatId).collection("mensajes")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) return@addSnapshotListener

                val mensajes = value?.toObjects(Mensaje::class.java) ?: emptyList()
                adapter.submitList(mensajes)
                if (mensajes.isNotEmpty()) {
                    binding.recyclerMensajes.scrollToPosition(mensajes.size - 1)
                }
            }
    }

    private fun setupEnviar() {
        binding.btnEnviar.setOnClickListener {
            val texto = binding.etMensaje.text.toString().trim()
            if (texto.isEmpty()) return@setOnClickListener

            val miUid = auth.currentUser?.uid ?: return@setOnClickListener
            val timestamp = System.currentTimeMillis()

            val mensaje = Mensaje(texto, miUid, timestamp)

            // 1. Guardar mensaje
            db.collection("chats").document(chatId).collection("mensajes").add(mensaje)

            val updateData = mapOf(
                "ultimoMensaje" to texto,
                "timestamp" to timestamp,
                "usuarios" to listOf(miUid, vendedorId)
            )
            db.collection("chats").document(chatId).set(updateData, com.google.firebase.firestore.SetOptions.merge())

            binding.etMensaje.setText("")
        }
    }
}
