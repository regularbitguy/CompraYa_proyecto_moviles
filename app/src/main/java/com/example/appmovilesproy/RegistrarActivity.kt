package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class RegistrarActivity : AppCompatActivity() {

    private lateinit var btnRegistrar: Button
    private lateinit var btnIniciaSesion: MaterialButton
    private lateinit var btnRegistrate: MaterialButton
    private lateinit var btnFacebook: MaterialButton
    private lateinit var btnGoogle: MaterialButton
    private lateinit var auth: FirebaseAuth
    private lateinit var etNombre: TextInputEditText
    private lateinit var etCorreo: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var etConfirmar: TextInputEditText
    private lateinit var etDni: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }
    private fun asignarReferencias() {

        auth = FirebaseAuth.getInstance()
        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etContrasena = findViewById(R.id.etContrasena)
        etConfirmar = findViewById(R.id.etConfirmar)
        etDni = findViewById(R.id.etDni)

        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnIniciaSesion = findViewById(R.id.btnIniciaSesion)
        btnRegistrate = findViewById(R.id.btnRegistrate)
        btnFacebook = findViewById(R.id.btnFacebook)
        btnGoogle = findViewById(R.id.btnGoogle)

        btnRegistrar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            val confirmar = etConfirmar.text.toString().trim()

            if (correo.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (contrasena != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            registrarUsuario(correo, contrasena)
        }
        btnIniciaSesion.setOnClickListener {
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registrarUsuario(correo: String, contrasena: String) {
        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                    Toast.makeText(this, "Registro exitoso. Revisa tu correo para verificar tu cuenta.", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, RegistroExitosoActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
