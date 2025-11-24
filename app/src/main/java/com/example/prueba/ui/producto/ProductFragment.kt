package com.example.appmovilesproy.ui.producto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appmovilesproy.PerfilVendedorFragment
import com.example.appmovilesproy.R
import com.example.appmovilesproy.adapter.ProductoAdapter
import com.example.appmovilesproy.databinding.FragmentProductoBinding
import com.example.appmovilesproy.Producto
import com.example.prueba.ui.chats.ChatsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProductFragment : Fragment() {

    private var _binding: FragmentProductoBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val listaProductos = mutableListOf<Producto>()
    private lateinit var adapter: ProductoAdapter
    private var titulo: String? = null
    private var descripcion: String? = null
    private var precio: Double? = null
    private var imagenUrl: String? = null
    private var isFavorite = false
    private var productoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            productoId = it.getString("productoId")
            titulo = it.getString("titulo")
            descripcion = it.getString("descripcion")
            precio = it.getDouble("precio")
            imagenUrl = it.getString("imagenUrl")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductoBinding.inflate(inflater, container, false)

        // Mostrar datos del producto seleccionado
        binding.txtNombreProducto.text = titulo
        binding.txtDescripcion.text = descripcion
        binding.txtPrecio.text = "S/. $precio"

        Glide.with(requireContext()).load(imagenUrl).into(binding.imgProd)

        // Configurar RecyclerView de sugerencias
        adapter = ProductoAdapter(requireContext(), listaProductos)
        binding.rvProductosRecientes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProductosRecientes.adapter = adapter

        cargarProductosSugeridos()
        checkIfFavorite()
        referenciar()

        return binding.root
    }
    private fun referenciar(){
        // Botón atrás
        binding.btnBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.layoutPerfilVendedor.setOnClickListener {
            val fragment = PerfilVendedorFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment) // 'fragment_container' es el ID en tu MainActivity
                .addToBackStack(null)
                .commit()
        }
        binding.btnMessage.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChatsFragment())
                .addToBackStack(null)
                .commit()
        }
        // ⭐ Listener para el botón de wishlist
        binding.btnAddToWishlist.setOnClickListener {
            toggleWishlistStatus()
        }
    }
    private fun toggleWishlistStatus() {
        if (currentUser == null || productoId == null) {
            Toast.makeText(requireContext(), "Error: Usuario no autenticado o producto inválido.", Toast.LENGTH_SHORT).show()
            return
        }

        val wishlistRef = db.collection("usuarios").document(currentUser.uid).collection("wishlist").document(productoId!!)

        if (isFavorite) {
            wishlistRef.delete().addOnSuccessListener {
                isFavorite = false
                updateFavoriteIcon()
                Toast.makeText(requireContext(), "Eliminado de tu wishlist", Toast.LENGTH_SHORT).show()
            }
        } else {
            val wishlistItem = hashMapOf(
                "productoId" to productoId,
            )
            wishlistRef.set(wishlistItem).addOnSuccessListener {
                isFavorite = true
                updateFavoriteIcon()
                Toast.makeText(requireContext(), "¡Añadido a tu wishlist!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIfFavorite() {
        if (currentUser == null || productoId == null) return

        db.collection("usuarios").document(currentUser.uid).collection("wishlist").document(productoId!!).get().addOnSuccessListener {
            document ->
            isFavorite = document.exists()
            updateFavoriteIcon()
        }
    }

    private fun updateFavoriteIcon() {
        if (isFavorite) {
            binding.btnAddToWishlist.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.btnAddToWishlist.setImageResource(R.drawable.ic_favorite_border)
        }
    }
    private fun cargarProductosSugeridos() {
        db.collection("productos").limit(10).get().addOnSuccessListener { result ->
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
        fun newInstance(productoId: String,titulo: String, descripcion: String, precio: Double, imagenUrl: String): ProductFragment {
            val fragment = ProductFragment()
            val args = Bundle()
            args.putString("productoId", productoId)
            args.putString("titulo", titulo)
            args.putString("descripcion", descripcion)
            args.putDouble("precio", precio)
            args.putString("imagenUrl", imagenUrl)
            fragment.arguments = args
            return fragment
        }
    }
}
