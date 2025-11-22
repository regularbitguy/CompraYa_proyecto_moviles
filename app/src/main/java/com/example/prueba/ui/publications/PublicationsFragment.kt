package com.example.prueba.ui.publications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appmovilesproy.databinding.FragmentPublicationsBinding
import com.example.appmovilesproy.Producto
import com.example.appmovilesproy.adapter.ProductoAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PublicationsFragment : Fragment() {

    private var _binding: FragmentPublicationsBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val listaProductos = mutableListOf<Producto>()
    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublicationsBinding.inflate(inflater, container, false)

        adapter = ProductoAdapter(requireContext(), listaProductos)
        binding.rvMisPublicaciones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMisPublicaciones.adapter = adapter

        cargarMisPublicaciones()

        binding.btnRetroceder2.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }

    private fun cargarMisPublicaciones() {
        val usuarioId = auth.currentUser?.uid

        if (usuarioId == null) {
            Toast.makeText(requireContext(), "No se encontró usuario", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("productos")
            .whereEqualTo("usuarioId", usuarioId)
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                listaProductos.clear()
                for (doc in result) {
                    val producto = doc.toObject(Producto::class.java)
                    listaProductos.add(producto)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al cargar publicaciones", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
