package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appmovilesproy.databinding.ActivityIniciarSesionBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIniciarSesionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001 // Request code para el inicio de sesión con Google

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // 2. Configurar el cliente de Google Sign-In
        setupGoogleSignInClient()

        // 3. Configurar los listeners para los botones
        setupActionListeners()
    }

    private fun setupGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupActionListeners() {
        // Iniciar sesión con Correo y Contraseña
        binding.btnIniciarSesion.setOnClickListener {
            iniciarSesionConEmail()
        }

        // Iniciar sesión con Google
        binding.btnGoogle.setOnClickListener {
            iniciarSesionGoogle()
        }

        // Navegar a la pantalla de registro
        binding.btnRegistrate.setOnClickListener {
            startActivity(Intent(this, RegistrarActivity::class.java))
        }

        // Navegar a la pantalla de recuperar contraseña
        binding.btnOlvidastePass.setOnClickListener {
            startActivity(Intent(this, RecuperarContrasenaActivity::class.java))
        }
    }

    private fun iniciarSesionConEmail() {
        val correo = binding.txtCorreo.text.toString().trim()
        val contrasena = binding.txtContrasena.text.toString().trim()

        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Si es exitoso, guarda la preferencia y navega al Home
                guardarPreferenciaRecordarme(binding.chBoxRecordarme.isChecked)
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                irAMainActivity()
            } else {
                // Si falla, muestra un error
                Toast.makeText(this, "Error de autenticación: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun iniciarSesionGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Recibe el resultado del inicio de sesión con Google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthConGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Error con Google Sign-In: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthConGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                guardarPreferenciaRecordarme(binding.chBoxRecordarme.isChecked)
                Toast.makeText(this, "Bienvenido, ${user?.displayName}", Toast.LENGTH_SHORT).show()
                irAMainActivity()
            } else {
                Toast.makeText(this, "Error de autenticación: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun guardarPreferenciaRecordarme(recordar: Boolean) {
        val prefs = getSharedPreferences("userPrefs", MODE_PRIVATE)
        prefs.edit().putBoolean("recordarme", recordar).apply()
    }

    private fun irAMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        // Limpia el historial para que el usuario no pueda volver a la pantalla de login
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Cierra IniciarSesionActivity
    }
}
