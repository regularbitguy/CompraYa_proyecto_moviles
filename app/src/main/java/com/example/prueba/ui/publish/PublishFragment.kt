package com.example.prueba.ui.publish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmovilesproy.R
import com.example.appmovilesproy.databinding.FragmentPublishBinding
import android.widget.Toast
import com.example.prueba.ui.chats.ChatsFragment
import com.example.prueba.ui.publish.PublishSuccessFragment

class PublishFragment : Fragment() {

    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentPublishBinding.inflate(inflater, container, false)

        // Botón para retroceder (iconBuscar)
        binding.btnRetroceder2.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnPublicar.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val precio = binding.etPrecio.text.toString()
            val categoria = binding.etCategoria.text.toString()
            val estado = binding.etEstado.text.toString()
            val descripcion = binding.etDescripcion.text.toString()

            if (titulo.isEmpty() || precio.isEmpty() || categoria.isEmpty() ||
                estado.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Navegar al fragmento de confirmación
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PublishSuccessFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}