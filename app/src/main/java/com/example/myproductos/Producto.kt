package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Producto : AppCompatActivity() {

    private lateinit var recyclerSugerencias: RecyclerView
    private lateinit var adapter: SugerenciaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        recyclerSugerencias = findViewById(R.id.recyclerSugerencias)

        // Configura el RecyclerView en modo horizontal
        recyclerSugerencias.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val lista = listOf(
            Sugerencia("Teclado Logitech", "Teclado", "S/. 250", R.drawable.teclado),
            Sugerencia("Mouse Redragon", "Mouse", "S/. 120", R.drawable.teclado),
            Sugerencia("Audífono HyperX", "Audífono", "S/. 180", R.drawable.teclado)
        )

        adapter = SugerenciaAdapter(lista)
        recyclerSugerencias.adapter = adapter

        val perfilVendedorLayout = findViewById<android.view.View>(R.id.layoutPerfilVendedor)

        perfilVendedorLayout.setOnClickListener {
            // Crear intent y abrir la nueva actividad
            val intent = Intent(this, PerfilVendedorActivity::class.java)
            startActivity(intent)

            // (Opcional) Animación suave de transición
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
