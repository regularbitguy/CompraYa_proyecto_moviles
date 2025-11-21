package com.example.appmovilesproy.ui.producto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appmovilesproy.adapter.ProductoAdapter
import com.example.appmovilesproy.databinding.FragmentProductoBinding
import com.example.appmovilesproy.model.Producto
import com.google.firebase.firestore.FirebaseFirestore

class ProductFragment : Fragment() {

    private var _binding: FragmentProductoBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    private val listaProductos = mutableListOf<Producto>()
    private lateinit var adapter: ProductoAdapter

    private var titulo: String? = null
    private var descripcion: String? = null
    private var precio: Double? = null
    private var imagenUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperando argumentos
        arguments?.let {
            titulo = it.getString("titulo")
            descripcion = it.getString("descripcion")
            precio = it.getDouble("precio")
            imagenUrl = it.getString("imagenUrl")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductoBinding.inflate(inflater, container, false)

        // Mostrar datos del producto seleccionado
        binding.txtNombreProducto.text = titulo
        binding.txtDescripcion.text = descripcion
        binding.txtPrecio.text = "S/. $precio"

        Glide.with(requireContext())
            .load(imagenUrl)
            .into(binding.imgProd)

        // Configurar RecyclerView de sugerencias
        adapter = ProductoAdapter(requireContext(), listaProductos)
        binding.rvProductosRecientes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProductosRecientes.adapter = adapter

        cargarProductosSugeridos()

        // Botón atrás
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        return binding.root
    }

    private fun cargarProductosSugeridos() {
        db.collection("productos")
            .limit(10)   // puedes ajustar la cantidad
            .get()
            .addOnSuccessListener { result ->
                listaProductos.clear()
                for (doc in result) {
                    val p = doc.toObject(Producto::class.java)
                    listaProductos.add(p)
                }
                adapter.notifyDataSetChanged()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(titulo: String, descripcion: String, precio: Double, imagenUrl: String): ProductFragment {
            val fragment = ProductFragment()
            val args = Bundle()
            args.putString("titulo", titulo)
            args.putString("descripcion", descripcion)
            args.putDouble("precio", precio)
            args.putString("imagenUrl", imagenUrl)
            fragment.arguments = args
            return fragment
        }
    }
}
