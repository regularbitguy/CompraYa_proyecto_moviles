package com.example.prueba.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovilesproy.R
import com.example.appmovilesproy.databinding.FragmentHomeBinding
import com.example.appmovilesproy.ProductoActivity
import com.example.appmovilesproy.adapter.ProductoAdapter
import com.example.appmovilesproy.model.Producto
import com.example.prueba.Buscar
import com.example.prueba.CategoriasActivity
import com.example.prueba.ui.chats.ChatsFragment
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    private val listaProductos = mutableListOf<Producto>()
    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = ProductoAdapter(requireContext(), listaProductos)
        binding.rvProductosRecientes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProductosRecientes.adapter = adapter

        cargarProductos()

        binding.btnBuscarCategoria.setOnClickListener {
            startActivity(Intent(requireContext(), Buscar::class.java))
        }

        binding.btnChats.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChatsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnVerCategorias.setOnClickListener {
            startActivity(Intent(requireContext(), CategoriasActivity::class.java))
        }

        return binding.root
    }

    private fun cargarProductos() {
        db.collection("productos").orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING).get().addOnSuccessListener {
            result ->
            listaProductos.clear()
            for (doc in result) {
                val producto = doc.toObject(Producto::class.java)
                listaProductos.add(producto)
            }
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Error al cargar productos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
