package com.example.prueba.ui.publications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovilesproy.databinding.FragmentPublicationsBinding
import com.example.appmovilesproy.Producto
// import com.example.appmovilesproy.adapter.ProductoAdapter // <-- YA NO USAMOS ESTE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PublicationsFragment : Fragment() {

    private var _binding: FragmentPublicationsBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val listaProductos = mutableListOf<Producto>()

    // CAMBIO 1: Usamos PublicacionAdapter, no ProductoAdapter
    private lateinit var adapter: PublicacionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPublicationsBinding.inflate(inflater, container, false)

        // CAMBIO 2: Inicializamos el PublicacionAdapter pasando las funciones de click (por ahora vacías o con Toast)
        adapter = PublicacionAdapter(listaProductos,
            onEditClick = { producto ->
                Toast.makeText(context, "Editar ${producto.titulo}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { producto ->
                Toast.makeText(context, "Eliminar ${producto.titulo}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvMisPublicaciones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMisPublicaciones.adapter = adapter

        cargarMisPublicaciones()

        binding.btnRetroceder2.setOnClickListener {
            // Si estás usando navegación manual en MainActivity, esto puede que no funcione como esperas.
            // Lo ideal sería: parentFragmentManager.popBackStack()
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }

    private fun cargarMisPublicaciones() {
        val usuarioId = auth.currentUser?.uid

        if (usuarioId == null) {
            Toast.makeText(requireContext(), "No se encontró usuario", Toast.LENGTH_SHORT).show()
            return
        }

        // Asegúrate de que el campo en Firestore sea "usuarioId" (tal cual lo escribiste)
        db.collection("productos")
            .whereEqualTo("usuarioId", usuarioId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                listaProductos.clear()
                for (doc in result) {
                    val producto = doc.toObject(Producto::class.java)
                    // Es importante guardar el ID del documento por si quieres editar/borrar luego
                    producto.id = doc.id
                    listaProductos.add(producto)
                }

                if (listaProductos.isEmpty()) {
                    Toast.makeText(context, "No tienes publicaciones aún", Toast.LENGTH_SHORT).show()
                }

                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Si falla, a veces es porque falta crear el índice en Firebase console (ver Logcat)
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
