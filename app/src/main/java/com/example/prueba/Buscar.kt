package com.example.prueba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prueba.databinding.ActivityMenuBuscarBinding

class Buscar : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBuscarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBuscarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔙 Botón de retroceso
        binding.iconBuscar.setOnClickListener {
            finish() // Cierra la pantalla actual y regresa a la anterior
        }

        // ❌ Botón de limpiar búsqueda
        binding.btnLimpiar.setOnClickListener {
            binding.etBusqueda.text.clear() // Limpia el campo de búsqueda
        }
    }
}