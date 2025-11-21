package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ReportarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportar)
        asignarReferencias()
    }
    private fun asignarReferencias(){
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnEnviar = findViewById<Button>(R.id.btnEnviarReporte)

        btnBack.setOnClickListener {
            val intent = Intent(this, PerfilVendedorActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnEnviar.setOnClickListener {
            mostrarMensaje("Reporte enviado")
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