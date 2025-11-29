package com.example.appmovilesproy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmovilesproy.Producto
import com.example.appmovilesproy.R
import com.example.appmovilesproy.databinding.ItemProductoRecienteBinding
import com.example.appmovilesproy.ui.producto.ProductFragment

class ProductoAdapter(
    private val context: Context,
    private val listaProductos: List<Producto>,
    private val onClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(val binding: ItemProductoRecienteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoRecienteBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        holder.binding.tvNombreProducto.text = producto.titulo
        holder.binding.tvDescripcion.text = producto.descripcion
        holder.binding.tvPrecio.text = "S/ ${producto.precio ?: 0.0}"

        Glide.with(context)
            .load(producto.imagenUrl)
            .placeholder(R.drawable.img)
            .into(holder.binding.imgProducto)

        // Obtener correctamente vendedorId
        val vendedor = when {
            !producto.vendedorId.isNullOrEmpty() -> producto.vendedorId
            !producto.usuarioId.isNullOrEmpty() -> producto.usuarioId
            else -> ""
        }

        // Lo guardamos en el modelo para no perderlo
        producto.vendedorId = vendedor

        holder.itemView.setOnClickListener {
            val fragment = ProductFragment.newInstance(
                productoId = producto.id,
                titulo = producto.titulo ?: "",
                descripcion = producto.descripcion ?: "",
                precio = producto.precio ?: 0.0,
                imagenUrl = producto.imagenUrl ?: "",
                vendedorId = vendedor
            )

            val activity = context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = listaProductos.size
}
