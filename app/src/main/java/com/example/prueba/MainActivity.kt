package com.example.prueba

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rvProductos = findViewById<RecyclerView>(R.id.rvProductosRecientes)
        rvProductos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val lista = listOf(
            Producto("Logitech GT12", "Mouse Wireless", 345.0, R.drawable.img_mouse),
            Producto("Monitor Ocelot Gaming OM-C32 ", "Monitor Gaming", 569.00, R.drawable.img_monitor),
            Producto("Logitech G15 RGB", "Wireless Keyboard", 479.0, R.drawable.img_teclado),
            Producto("Vsg Aguila", "Mouse Gaming", 69.0, R.drawable.img_mouse2),
            Producto("Game Extreme", "Silla Gamer", 699.0, R.drawable.img_silla),
            Producto("Logitech GT12", "Mouse Wireless", 345.0, R.drawable.img_mouse),
            Producto("Monitor Ocelot Gaming OM-C32 ", "Monitor Gaming", 569.00, R.drawable.img_monitor),
            Producto("Logitech G15 RGB", "Wireless Keyboard", 479.0, R.drawable.img_teclado),
            Producto("Game Extreme", "Silla Gamer", 699.0, R.drawable.img_silla),
            Producto("Vsg Aguila", "Mouse Gaming", 69.0, R.drawable.img_mouse2)
        )
        rvProductos.adapter = ProductoAdapter(lista)
        asignarReferencias()
    }
    private fun asignarReferencias(){
        // Botón Buscar
        binding.btnBuscar.setOnClickListener {
            val intent = Intent(this, Buscar::class.java)
            startActivity(intent)
        }
    }
}
