package com.example.prueba.ui.publish

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.appmovilesproy.R
import com.example.appmovilesproy.databinding.FragmentPublishBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class PublishFragment : Fragment() {

    private lateinit var binding: FragmentPublishBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private var imageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1001
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPublishBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        binding.imgProducto.setOnClickListener { openGallery() }
        binding.btnPublicar.setOnClickListener { publicarProducto() }

        return binding.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            Glide.with(this).load(imageUri).into(binding.imgProducto)
        }
    }

    private fun publicarProducto() {
        val titulo = binding.etTitulo.text.toString().trim()
        val precioTexto = binding.etPrecio.text.toString().trim()

        if (precioTexto.isEmpty()) {
            binding.etPrecio.error = "Ingresa un precio"
            return
        }

        val precio = precioTexto.toDoubleOrNull() ?: 0.0
        val categoria = binding.etCategoria.text.toString().trim()
        val estado = binding.etEstado.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()

        if (titulo.isEmpty() || precio == null || categoria.isEmpty() ||
            estado.isEmpty() || descripcion.isEmpty() || imageUri == null) {
            Toast.makeText(requireContext(), "Por favor completa todos los campos e incluye una imagen", Toast.LENGTH_SHORT).show()
            return
        }

        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Publicando producto...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val userId = auth.currentUser?.uid ?: return
        val imageRef = storage.reference.child("productos/${UUID.randomUUID()}.jpg")

        imageUri?.let { uri ->
            imageRef.putFile(uri).addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->

                    val producto = hashMapOf(
                        "titulo" to titulo,
                        "precio" to precio,
                        "categoria" to categoria,
                        "estado" to estado,
                        "descripcion" to descripcion,
                        "imagenUrl" to downloadUrl.toString(),
                        "usuarioId" to userId,
                        "timestamp" to System.currentTimeMillis()
                    )

                    db.collection("productos").add(producto).addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(requireContext(), "Producto publicado con éxito", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                    }
                        .addOnFailureListener { e ->
                            progressDialog.dismiss()
                            Toast.makeText(requireContext(), "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }.addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "Error al subir imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun limpiarCampos() {
        binding.etTitulo.setText("")
        binding.etPrecio.setText("")
        binding.etCategoria.setText("")
        binding.etEstado.setText("")
        binding.etDescripcion.setText("")
        binding.imgProducto.setImageResource(R.drawable.img_agregarfoto)
        imageUri = null
    }
}