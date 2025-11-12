package com.example.prueba.ui.publications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


import com.bumptech.glide.Glide

import com.example.appmovilesproy.R
import com.example.appmovilesproy.model.Producto

class PublicacionAdapter(private val lista: List<Producto>) : RecyclerView.Adapter<PublicacionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { val view = LayoutInflater.from(parent.context).inflate(R.layout.item_publicacion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]

        Glide.with(holder.itemView.context)
            .load(producto.imagenUrl)
            .placeholder(R.drawable.img_teclado)
            .error(R.drawable.img_teclado)
            .into(holder.imgProducto)


        holder.tvNombreProducto.text = producto.titulo
        holder.tvDescripcion.text = producto.descripcion
        holder.tvPrecio.text = String.format("S/. %.2f", producto.precio)
    }
    override fun getItemCount(): Int = lista.size
}
