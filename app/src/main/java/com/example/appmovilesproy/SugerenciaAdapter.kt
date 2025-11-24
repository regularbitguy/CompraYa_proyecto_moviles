package com.example.appmovilesproy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class SugerenciaAdapter(private val lista: List<Sugerencia>) : RecyclerView.Adapter<SugerenciaAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img = view.findViewById<ImageView>(R.id.imgSugerencia)
        val nombre = view.findViewById<TextView>(R.id.txtNombreSugerencia)
        val tipo = view.findViewById<TextView>(R.id.txtTipoSugerencia)
        val precio = view.findViewById<TextView>(R.id.txtPrecioSugerencia)
        val btnAgregar = view.findViewById<Button>(R.id.btnAgregarSugerencia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sugerencia, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.img.setImageResource(item.imagenResId)
        holder.nombre.text = item.nombre
        holder.tipo.text = item.tipo
        holder.precio.text = item.precio
        holder.btnAgregar.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Agregado: ${item.nombre}", Toast.LENGTH_SHORT).show()
        }
    }
}
