package com.example.appmovilesproy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView // <--- Importante importar ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class InfoPersonalFragment : Fragment() {

    // Declaramos como ImageView porque así está en el XML
    private lateinit var btnBack: ImageView
    private lateinit var btnGuardar: Button

    // ... declara tus EditText aquí si los usas ...

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        asignarReferencias(view)
    }

    private fun asignarReferencias(view: View) {
        // Aquí es donde daba el error. Ahora lo buscamos correctamente.
        btnBack = view.findViewById(R.id.btnBack)
        btnGuardar = view.findViewById(R.id.btnGuardar)

        // Configurar el botón atrás
        btnBack.setOnClickListener {
            // Usamos popBackStack para volver atrás manualmente
            parentFragmentManager.popBackStack()
        }

        // Configurar botón guardar
        btnGuardar.setOnClickListener {
            Toast.makeText(context, "Guardando cambios...", Toast.LENGTH_SHORT).show()
            // Aquí tu lógica de guardado
        }
    }
}
