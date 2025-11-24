package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovilesproy.databinding.FragmentPerfilVendedorBinding

class PerfilVendedorFragment : Fragment() {

    private var _binding: FragmentPerfilVendedorBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPerfilVendedorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarBotones()
        configurarRecycler()

    }

    private fun configurarBotones() {
        // En PerfilVendedorFragment.kt -> configurarBotones()

        binding.btnBack.setOnClickListener {
            // Volver atrás manual
            parentFragmentManager.popBackStack()
        }

        binding.btnReportar.setOnClickListener {
            // Ir a reportar manual
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, ReportarFragment()).addToBackStack(null).commit()
        }
    }
    private fun configurarRecycler() {
        binding.recyclerProductosVendedor.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val lista = listOf(
            Sugerencia("Teclado Gamer", "Teclado", "S/. 250", R.drawable.img_teclado),
            Sugerencia("Mouse RGB", "Mouse", "S/. 120", R.drawable.img_mouse)
        )

        val adapter = SugerenciaAdapter(lista)
        binding.recyclerProductosVendedor.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}