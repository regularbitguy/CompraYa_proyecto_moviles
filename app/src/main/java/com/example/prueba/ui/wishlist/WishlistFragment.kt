package com.example.appmovilesproy.ui.wishlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appmovilesproy.Producto
import com.example.appmovilesproy.adapter.ProductoAdapter
import com.example.appmovilesproy.databinding.FragmentWishlistBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    private lateinit var productoAdapter: ProductoAdapter
    private val wishlistProducts = mutableListOf<Producto>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadWishlist()
    }

    private fun setupRecyclerView() {
        // Usamos el mismo adapter que ya tienes
        productoAdapter = ProductoAdapter(requireContext(), wishlistProducts)
        binding.rvWishlist.apply {
            // Puedes usar GridLayoutManager para una vista de cuadrícula
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productoAdapter
        }
    }

    private fun loadWishlist() {
        binding.progressBarWishlist.isVisible = true
        binding.tvEmptyWishlist.isVisible = false
        binding.rvWishlist.isVisible = false

        if (currentUser == null) {
            binding.progressBarWishlist.isVisible = false
            binding.tvEmptyWishlist.text = "Debes iniciar sesión para ver tu wishlist."
            binding.tvEmptyWishlist.isVisible = true
            return
        }
        db.collection("usuarios").document(currentUser.uid).collection("wishlist")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    binding.progressBarWishlist.isVisible = false
                    binding.tvEmptyWishlist.isVisible = true
                    return@addOnSuccessListener
                }

                val productIds = documents.map { it.id }

                fetchProductDetails(productIds)
            }
            .addOnFailureListener { e ->
                Log.w("WishlistFragment", "Error al obtener la wishlist", e)
                binding.progressBarWishlist.isVisible = false
                binding.tvEmptyWishlist.text = "Error al cargar la lista."
                binding.tvEmptyWishlist.isVisible = true
            }
    }

    private fun fetchProductDetails(productIds: List<String>) {
        if (productIds.isEmpty()) return

        db.collection("productos").whereIn(FieldPath.documentId(), productIds).get().addOnSuccessListener {
            productDocuments ->
                wishlistProducts.clear()
                for (doc in productDocuments) {
                    val producto = doc.toObject<Producto>()
                    wishlistProducts.add(producto)
                }
                productoAdapter.notifyDataSetChanged()
                binding.progressBarWishlist.isVisible = false
                binding.rvWishlist.isVisible = true
            }
            .addOnFailureListener { e ->
                Log.w("WishlistFragment", "Error al obtener detalles de productos", e)
                binding.progressBarWishlist.isVisible = false
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
