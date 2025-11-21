package com.example.appmovilesproy

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.appmovilesproy.databinding.ActivityRegistrarBinding // Importa la clase de View Binding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class RegistrarActivity : AppCompatActivity() {

    // Se recomienda usar View Binding para evitar findViewById repetitivos
    private lateinit var binding: ActivityRegistrarBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    // Lanzador moderno para el resultado del inicio de sesión con Google
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthConGoogle(account)
                } catch (e: ApiException) {
                    Toast.makeText(this, "Error con Google Sign-In: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicialización con View Binding
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configurar el cliente de Google Sign-In
        configurarGoogleSignIn()

        // Configurar los listeners de los botones
        configurarListeners()
    }

    private fun configurarGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun configurarListeners() {
        // Botón para registrarse con correo y contraseña
        binding.btnRegistrar.setOnClickListener {
            registrarUsuarioConEmail()
        }

        // Botón para iniciar sesión con Google
        binding.btnGoogle.setOnClickListener {
            iniciarRegistroConGoogle()
        }

        // Botón para ir a la pantalla de Iniciar Sesión
        binding.btnIniciaSesion.setOnClickListener {
            startActivity(Intent(this, IniciarSesionActivity::class.java))
        }
    }

    private fun registrarUsuarioConEmail() {
        val correo = binding.etCorreo.text.toString().trim()
        val contrasena = binding.etContrasena.text.toString().trim()
        val confirmar = binding.etConfirmar.text.toString().trim()

        if (correo.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (contrasena != confirmar) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Envío de correo de verificación (buena práctica)
                    task.result.user?.sendEmailVerification()
                    Toast.makeText(this, "Registro exitoso. Revisa tu correo para verificar la cuenta.", Toast.LENGTH_LONG).show()
                    // Redirigir a una pantalla que confirme el registro
                    startActivity(Intent(this, RegistroExitosoActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error en el registro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun iniciarRegistroConGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthConGoogle(account: GoogleSignInAccount?) {
        if (account?.idToken == null) {
            Toast.makeText(this, "Error: No se pudo obtener el token de Google.", Toast.LENGTH_SHORT).show()
            return
        }

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // ¡Punto clave! Guardar la preferencia para futuras sesiones
                    guardarPreferenciaRecordarme(true)

                    Toast.makeText(this, "Bienvenido, ${auth.currentUser?.displayName}", Toast.LENGTH_SHORT).show()
                    irAMainActivity()
                } else {
                    Toast.makeText(this, "Error de autenticación con Firebase: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun guardarPreferenciaRecordarme(recordar: Boolean) {
        val prefs: SharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        prefs.edit().putBoolean("recordarme", recordar).apply()
    }

    private fun irAMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        // Limpia el historial para que el usuario no pueda volver a esta pantalla
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
