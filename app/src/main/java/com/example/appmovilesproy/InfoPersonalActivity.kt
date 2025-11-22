package com.example.appmovilesproy

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appmovilesproy.R



class InfoPersonalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_personal)
        asignarReferencias()

    }
    private fun asignarReferencias(){
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        btnGuardar.setOnClickListener {
            mostrarMensaje("Datos guardados correctamente")
        }
    }
    private fun mostrarMensaje(mensaje: String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar",{dialog, which -> finish()})
        ventana.create().show()
    }
}
