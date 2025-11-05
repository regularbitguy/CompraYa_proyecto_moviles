package com.example.prueba.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba.ProductoActivity
import com.example.appmovilesproy.databinding.ItemProductoRecienteBinding
import com.example.prueba.Producto


class ProductoAdapter(private val context: Context, private val listaProductos: List<Producto>) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductoRecienteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductoRecienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = listaProductos[position]

        // Asignamos datos a la vista
        holder.binding.tvNombreProducto.text = producto.nombre
        holder.binding.tvDescripcion.text = producto.descripcion
        holder.binding.tvPrecio.text = "S/. ${producto.precio}"
        holder.binding.imgProducto.setImageResource(producto.imagen)

        // Al hacer clic en el producto → abrir ProductoActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductoActivity::class.java)
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("descripcion", producto.descripcion)
            intent.putExtra("precio", producto.precio)
            intent.putExtra("imagen", producto.imagen)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listaProductos.size
}
