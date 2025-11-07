package com.example.prueba.ui.publications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prueba.Producto

import com.example.appmovilesproy.R
import com.example.appmovilesproy.databinding.FragmentPublicationsBinding

class PublicationsFragment : Fragment() {

    private var _binding: FragmentPublicationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublicationsBinding.inflate(inflater, container, false)

        // Lista reutilizando los productos de prueba
        val lista = listOf(
            Producto("Logitech GT12", "Mouse Wireless", 345.0, R.drawable.img_mouse),
            Producto("Monitor Ocelot Gaming OM-C32", "Monitor Gaming", 569.00, R.drawable.img_monitor),
            Producto("Logitech G15 RGB", "Wireless Keyboard", 479.0, R.drawable.img_teclado),
            Producto("VSG Aguila", "Mouse Gaming", 69.0, R.drawable.img_mouse2),
            Producto("Game Extreme", "Silla Gamer", 699.0, R.drawable.img_silla)
        )

        binding.rvMisPublicaciones.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMisPublicaciones.adapter = PublicacionAdapter(lista)

        // Botón retroceder
        binding.btnRetroceder2.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
