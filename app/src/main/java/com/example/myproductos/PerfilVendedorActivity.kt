package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PerfilVendedorActivity : AppCompatActivity() {

    private lateinit var recyclerProductosVendedor: RecyclerView
    private lateinit var adapter: SugerenciaAdapter
    private lateinit var btnBack: ImageButton
    private lateinit var btnReportar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_vendedor)

        asignarReferencias()

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
    }
    private fun asignarReferencias(){
        recyclerProductosVendedor = findViewById(R.id.recyclerProductosVendedor)
        btnBack = findViewById(R.id.btnBack)
        btnReportar = findViewById(R.id.btnReportar)

        btnBack.setOnClickListener {
            val intent = Intent(this, Producto::class.java)
            startActivity(intent)
        }

        btnReportar.setOnClickListener {
            val intent = Intent(this, activity_reportar::class.java)
            startActivity(intent)
            finish()
        }
    }
}