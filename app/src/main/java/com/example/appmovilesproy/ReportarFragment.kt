package com.example.appmovilesproy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class ReportarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reportar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        asignarReferencias(view)
    }

    private fun asignarReferencias(view: View) {
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        val btnEnviar = view.findViewById<Button>(R.id.btnEnviarReporte)

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        btnEnviar.setOnClickListener {
            mostrarMensaje("Reporte enviado")
        }
    }
    private fun mostrarMensaje(mensaje: String) {
        val ventana = AlertDialog.Builder(requireContext())
        ventana.setTitle("Mensaje")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar") { dialog, which ->
            findNavController().navigateUp()
        }
        ventana.create().show()
    }
}