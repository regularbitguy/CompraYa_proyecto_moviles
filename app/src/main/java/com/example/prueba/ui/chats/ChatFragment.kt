package com.example.prueba.ui.chats

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovilesproy.databinding.FragmentChatsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.String

class ChatFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var adapter: ChatAdapter
    private val listaChats = mutableListOf<ChatItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)

        setupRecyclerView()
        cargarChats()
        return binding.root
    }

    private fun setupRecyclerView() {
        // Pasamos la lambda para manejar el clic
        adapter = ChatAdapter(listaChats) { chatItem ->
            val intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("chatId", chatItem.chatId)
            intent.putExtra("vendedorId", chatItem.usuarioId) // Pasamos el ID del OTRO usuario
            startActivity(intent)
        }
        binding.recyclerChats.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerChats.adapter = adapter
    }

    private fun cargarChats() {
        val miUid = auth.currentUser?.uid ?: return

        db.collection("chats")
            .whereArrayContains("usuarios", miUid)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, error ->
                if (error != null) return@addSnapshotListener

                listaChats.clear()

                for (doc in snap!!.documents) {
                    // Cast seguro
                    val usuarios = doc.get("usuarios") as? List<String> ?: emptyList()
                    if (usuarios.isEmpty()) continue

                    val otroUid = usuarios.firstOrNull { it != miUid } ?: miUid
                    val time = doc.getLong("timestamp") ?: 0
                    val horaFormateada = SimpleDateFormat("HH:mm", Locale.getDefault()).format(java.util.Date(time))

                    // Corrección del error de nulos: Usamos el operador Elvis (?: "")
                    val chatItem = ChatItem(
                        chatId = doc.id,
                        usuarioId = otroUid,
                        nombre = "Cargando...",
                        fotoPerfil = null, // Aquí sí aceptamos null porque en el data class pusimos String?
                        ultimoMensaje = doc.getString("ultimoMensaje") ?: "", // Si es null, pon texto vacío
                        hora = horaFormateada
                    )

                    listaChats.add(chatItem)
                }

                adapter.notifyDataSetChanged()
                cargarNombresUsuarios()
            }
    }

    private fun cargarNombresUsuarios() {
        listaChats.forEachIndexed { index, item ->
            db.collection("usuarios").document(item.usuarioId).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        item.nombre = doc.getString("nombre") ?: "Usuario"

                        // Corrección: fotoPerfil acepta nulos, así que esto ya no da error
                        item.fotoPerfil = doc.getString("fotoPerfil")

                        adapter.notifyItemChanged(index)
                    }
                }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
