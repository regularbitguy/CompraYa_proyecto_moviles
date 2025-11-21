package com.example.appmovilesproy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmovilesproy.R
import com.example.appmovilesproy.model.Producto
import com.example.appmovilesproy.ui.producto.ProductFragment

class ProductoAdapter(
    private val context: Context,
    private val listaProductos: List<Producto>
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imgProducto)
        val nombre: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val precio: TextView = itemView.findViewById(R.id.tvPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_producto_reciente, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        holder.nombre.text = producto.titulo
        holder.descripcion.text = producto.descripcion
        holder.precio.text = "S/ ${producto.precio ?: 0.0}"

        Glide.with(context)
            .load(producto.imagenUrl)
            .placeholder(R.drawable.img_teclado)
            .into(holder.imagen)

        holder.itemView.setOnClickListener {

            val fragment = ProductFragment.newInstance(
                titulo = producto.titulo ?: "",
                descripcion = producto.descripcion ?: "",
                precio = producto.precio ?: 0.0,
                imagenUrl = producto.imagenUrl ?: ""
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
