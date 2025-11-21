package com.example.appmovilesproy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RecuperarContrasenaActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etCorreo: TextInputEditText
    private lateinit var btnEnviarCorreo: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasena)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Referencias de vistas
        etCorreo = findViewById(R.id.etCorreo)
        btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo)

        // Acción del botón
        btnEnviarCorreo.setOnClickListener {
            val correo = etCorreo.text.toString().trim()

            if (correo.isEmpty()) {
                etCorreo.error = "Ingresa tu correo"
                return@setOnClickListener
            }

            // Enviar correo de restablecimiento con Firebase
            auth.sendPasswordResetEmail(correo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,
                            "Se envió un enlace a tu correo para restablecer la contraseña.",
                            Toast.LENGTH_LONG
                        ).show()
                        finish() // Cierra la actividad
                    } else {
                        Toast.makeText(this,
                            "Error: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}