package com.example.prueba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmovilesproy.databinding.ActivityProductoBinding

class ProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 Recibir los datos del producto desde el intent
        val nombre = intent.getStringExtra("nombre")
        val precio = intent.getStringExtra("precio")
        val descripcion = intent.getStringExtra("descripcion")
        val imagenRes = intent.getIntExtra("imagen", 0)

        // 🔹 Asignar los datos a la vista
        binding.txtNombreProducto.text = nombre
        binding.txtPrecio.text = precio
        binding.txtDescripcion.text = descripcion
        if (imagenRes != 0) binding.imgVendedor.setImageResource(imagenRes)

        // 🔹 Botón volver
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}