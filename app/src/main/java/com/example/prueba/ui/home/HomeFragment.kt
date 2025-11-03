package com.example.prueba.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prueba.Buscar
import com.example.prueba.CategoriasActivity
import com.example.prueba.Producto
import com.example.prueba.ProductoAdapter
import com.example.prueba.R
import com.example.prueba.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Configurar RecyclerView
        val lista = listOf(
            Producto("Logitech GT12", "Mouse Wireless", 345.0, R.drawable.img_mouse),
            Producto("Monitor Ocelot Gaming OM-C32 ", "Monitor Gaming", 569.00, R.drawable.img_monitor),
            Producto("Logitech G15 RGB", "Wireless Keyboard", 479.0, R.drawable.img_teclado),
            Producto("Vsg Aguila", "Mouse Gaming", 69.0, R.drawable.img_mouse2),
            Producto("Game Extreme", "Silla Gamer", 699.0, R.drawable.img_silla),
            Producto("Logitech GT12", "Mouse Wireless", 345.0, R.drawable.img_mouse),
            Producto("Monitor Ocelot Gaming OM-C32 ", "Monitor Gaming", 569.00, R.drawable.img_monitor),
            Producto("Logitech G15 RGB", "Wireless Keyboard", 479.0, R.drawable.img_teclado),
            Producto("Vsg Aguila", "Mouse Gaming", 69.0, R.drawable.img_mouse2),
            Producto("Game Extreme", "Silla Gamer", 699.0, R.drawable.img_silla)
        )

        binding.rvProductosRecientes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProductosRecientes.adapter = ProductoAdapter(lista)

        // Configurar botón buscar
        binding.btnBuscar.setOnClickListener {
            val intent = Intent(requireContext(), Buscar::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnVerCategorias = view.findViewById<View>(R.id.btnCategorias)
        btnVerCategorias.setOnClickListener {
            val intent = Intent(requireContext(), CategoriasActivity::class.java)
            startActivity(intent)
        }
    }
}