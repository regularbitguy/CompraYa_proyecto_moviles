package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
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
            Sugerencia("Teclado Logitech", "Teclado", "S/. 250", R.drawable.img_teclado),
            Sugerencia("Mouse Redragon", "Mouse", "S/. 120", R.drawable.img_mouse),
            Sugerencia("Silla Gamer", "Silla", "S/. 400", R.drawable.img_silla)
        )
        adapter = SugerenciaAdapter(lista)
        recyclerProductosVendedor.adapter = adapter
    }
    private fun asignarReferencias(){
        recyclerProductosVendedor = findViewById(R.id.recyclerProductosVendedor)
        btnBack = findViewById(R.id.btnBack)
        btnReportar = findViewById(R.id.btnReportar)

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }



        btnReportar.setOnClickListener {
            val intent = Intent(this, ReportarActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}