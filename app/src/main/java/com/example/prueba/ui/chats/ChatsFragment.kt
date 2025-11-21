package com.example.prueba.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovilesproy.R
import com.example.appmovilesproy.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)

        val listaChats = listOf(
            Chat("Laura Gómez", "¿Todavía está disponible el producto?", "14:32", R.drawable.img_perfil),
            Chat("Carlos Pérez", "Gracias por la compra!", "13:15", R.drawable.img_perfil),
            Chat("Ana López", "¿Podrías enviarlo mañana?", "09:50", R.drawable.img_perfil)
        )

        binding.recyclerChats.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerChats.adapter = ChatsAdapter(listaChats)

        binding.btnRetrocederChats.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
