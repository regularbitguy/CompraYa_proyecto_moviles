package com.example.appmovilesproy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appmovilesproy.databinding.ActivityProductoBinding

class ProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
        val precio = intent.getDoubleExtra("precio", 0.0)
        val imagenUrl = intent.getStringExtra("imagenUrl")

        binding.txtNombreProducto.text = nombre
        binding.txtDescripcion.text = descripcion
        binding.txtPrecio.text = "S/ $precio"

        Glide.with(this)
            .load(imagenUrl)
            .placeholder(R.drawable.img_teclado)
            .into(binding.imgProd)
    }
}
