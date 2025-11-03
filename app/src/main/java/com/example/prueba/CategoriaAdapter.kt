package com.example.prueba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriaAdapter(private val categorias: List<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCategoria: ImageView = view.findViewById(R.id.imgCategoria)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreCategoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.tvNombre.text = categoria.nombre
        holder.imgCategoria.setImageResource(categoria.imagen)
    }

    override fun getItemCount(): Int = categorias.size
}
