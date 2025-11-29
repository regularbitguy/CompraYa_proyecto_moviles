package com.example.appmovilesproy.ui.producto

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.prueba.ui.chats.ChatActivity
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
    private var productoId: String? = null
    private var vendedorId: String? = null

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            productoId = it.getString("productoId")
            titulo = it.getString("titulo")
            descripcion = it.getString("descripcion")
            precio = it.getDouble("precio", 0.0)
            imagenUrl = it.getString("imagenUrl")
            vendedorId = it.getString("vendedorId")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductoBinding.inflate(inflater, container, false)

        // Mostrar datos del producto
        binding.txtNombreProducto.text = titulo
        binding.txtDescripcion.text = descripcion
        binding.txtPrecio.text = "S/. $precio"

        if (!imagenUrl.isNullOrEmpty()) {
            Glide.with(requireContext()).load(imagenUrl).into(binding.imgProd)
        }

        // Adaptador de sugerencias
        adapter = ProductoAdapter(requireContext(), listaProductos) { productoSeleccionado ->
            val vendedor = productoSeleccionado.vendedorId ?: productoSeleccionado.usuarioId ?: ""

            val fragment = ProductFragment.newInstance(
                productoId = productoSeleccionado.id,
                titulo = productoSeleccionado.titulo ?: "",
                descripcion = productoSeleccionado.descripcion ?: "",
                precio = productoSeleccionado.precio ?: 0.0,
                imagenUrl = productoSeleccionado.imagenUrl ?: "",
                vendedorId = vendedor
            )

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.rvProductosRecientes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProductosRecientes.adapter = adapter

        // Acciones
        setupBotones()

        cargarProductosSugeridos()
        checkIfFavorite()

        return binding.root
    }

    private fun setupBotones() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.layoutPerfilVendedor.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PerfilVendedorFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnMessage.setOnClickListener { binding.btnContactar.performClick() }

        binding.btnContactar.setOnClickListener {
            Log.d("CHAT_TEST", "vendedorId recibido en Fragment = '$vendedorId'")

            if (vendedorId.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Error: No se identificó al vendedor", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            iniciarChatConVendedor(vendedorId!!)
        }

        binding.btnAddToWishlist.setOnClickListener { toggleWishlistStatus() }
    }

    private fun iniciarChatConVendedor(vendedorId: String) {
        val miUid = currentUser?.uid ?: return

        val chatId = if (miUid < vendedorId) "${miUid}_${vendedorId}" else "${vendedorId}_${miUid}"

        val chatRef = db.collection("chats").document(chatId)

        chatRef.get().addOnSuccessListener { doc ->
            if (!doc.exists()) {
                // Crear chat nuevo
                val chatData = hashMapOf(
                    "usuarios" to listOf(miUid, vendedorId),
                    "ultimoMensaje" to "",
                    "timestamp" to System.currentTimeMillis()
                )
                chatRef.set(chatData)
            }

            abrirChatActivity(chatId, vendedorId)
        }
    }

    private fun abrirChatActivity(chatId: String, vendedorId: String) {
        val intent = Intent(requireContext(), ChatActivity::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("vendedorId", vendedorId)
        Log.d("CHAT_TEST", "🔹 Abriendo chat: chatId=$chatId vendedorId=$vendedorId")
        startActivity(intent)
    }

    private fun toggleWishlistStatus() {
        if (currentUser == null || productoId == null) return

        val wishlistRef = db.collection("usuarios")
            .document(currentUser.uid)
            .collection("wishlist")
            .document(productoId!!)

        if (isFavorite) {
            wishlistRef.delete().addOnSuccessListener {
                isFavorite = false
                updateFavoriteIcon()
            }
        } else {
            wishlistRef.set(mapOf("productoId" to productoId)).addOnSuccessListener {
                isFavorite = true
                updateFavoriteIcon()
            }
        }
    }

    private fun checkIfFavorite() {
        if (currentUser == null || productoId == null) return

        db.collection("usuarios")
            .document(currentUser.uid)
            .collection("wishlist")
            .document(productoId!!)
            .get()
            .addOnSuccessListener { doc ->
                isFavorite = doc.exists()
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
        fun newInstance(
            productoId: String,
            titulo: String,
            descripcion: String,
            precio: Double,
            imagenUrl: String,
            vendedorId: String?
        ): ProductFragment {
            val fragment = ProductFragment()
            val args = Bundle()
            args.putString("productoId", productoId)
            args.putString("titulo", titulo)
            args.putString("descripcion", descripcion)
            args.putDouble("precio", precio)
            args.putString("imagenUrl", imagenUrl)
            args.putString("vendedorId", vendedorId)
            fragment.arguments = args
            return fragment
        }
    }
}
