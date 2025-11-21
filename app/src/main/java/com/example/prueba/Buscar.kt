package com.example.prueba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmovilesproy.databinding.ActivityMenuBuscarBinding

class Buscar : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBuscarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBuscarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        asignarReferencias()
    }

    private fun asignarReferencias() {
        binding.btnRetroceder2.setOnClickListener {
            finish()
        }
        binding.btnLimpiar.setOnClickListener {
            binding.etBusqueda.text.clear()
        }
    }
}

