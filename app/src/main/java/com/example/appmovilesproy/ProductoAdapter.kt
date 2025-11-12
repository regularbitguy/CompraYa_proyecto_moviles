package com.example.appmovilesproy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmovilesproy.ProductoActivity
import com.example.appmovilesproy.R
import com.example.appmovilesproy.model.Producto

class ProductoAdapter(private val context: Context, private val listaProductos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imgProducto)
        val nombre: TextView = itemView.findViewById(R.id.tvNombreProducto)

        val precio: TextView = itemView.findViewById(R.id.tvPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_producto_reciente, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        holder.nombre.text = producto.nombre
        holder.precio.text = "S/ ${producto.precio}"

        Glide.with(context)
            .load(producto.imagenUrl)
            .placeholder(R.drawable.img_teclado)
            .into(holder.imagen)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductoActivity::class.java)
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("descripcion", producto.descripcion)
            intent.putExtra("precio", producto.precio)
            intent.putExtra("imagenUrl", producto.imagenUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listaProductos.size
}
