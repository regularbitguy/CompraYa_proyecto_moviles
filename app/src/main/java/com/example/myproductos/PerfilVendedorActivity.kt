package com.example.myproductos

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PerfilVendedorActivity : AppCompatActivity() {

    private lateinit var recyclerProductosVendedor: RecyclerView
    private lateinit var adapter: SugerenciaAdapter
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_vendedor)

        recyclerProductosVendedor = findViewById(R.id.recyclerProductosVendedor)
        btnBack = findViewById(R.id.btnBack)

        // Configurar el RecyclerView horizontal
        recyclerProductosVendedor.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Datos simulados (puedes usar los mismos de producto)
        val lista = listOf(
            Sugerencia("Teclado Logitech", "Teclado", "S/. 250", R.drawable.teclado),
            Sugerencia("Mouse Redragon", "Mouse", "S/. 120", R.drawable.teclado),
            Sugerencia("Audífono HyperX", "Audífono", "S/. 180", R.drawable.teclado)
        )

        adapter = SugerenciaAdapter(lista)
        recyclerProductosVendedor.adapter = adapter

        // Botón de retroceso
        btnBack.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }
}