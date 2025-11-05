package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class OlvideContrasenaActivity : AppCompatActivity() {
    private lateinit var txtCorreo: TextInputEditText
    private lateinit var btnEnviarCodigo: Button
    private lateinit var txtMensaje: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_olvide_contrasena)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()

    }
    private fun asignarReferencias() {

        txtCorreo= findViewById(R.id.txtCorreo) // Correo es un EditText
        btnEnviarCodigo = findViewById(R.id.btnEnviarCodigo) // El botón para enviar el código
        txtMensaje= findViewById(R.id.txtMensaje) // Mensaje es un TextView


        btnEnviarCodigo.setOnClickListener {
            val correo = txtCorreo.text.toString()

            if (correo.isNotEmpty()) {

                val intent = Intent(this, VerificacionActivity::class.java)
                startActivity(intent) // Redirige a la actividad de verificación
            } else {
                // Mostrar mensaje de error si el correo está vacío
                txtCorreo.error = "Por favor ingresa un correo válido." // Muestra un error en el campo de texto
            }
        }
    }
}