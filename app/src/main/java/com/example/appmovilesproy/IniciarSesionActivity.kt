package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthConGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Toast.makeText(this, "Error con Google Sign-In: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        setupGoogleSignInClient()
        setupActionListeners()
    }

    private fun setupGoogleSignInClient() {
        try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)
        } catch (e: Exception) {
            Toast.makeText(this, "Error al configurar Google Sign-In", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupActionListeners() {
        binding.btnIniciarSesion.setOnClickListener { iniciarSesionConEmail() }
        binding.btnGoogle.setOnClickListener { iniciarSesionGoogle() }

        binding.btnRegistrate.setOnClickListener {
            startActivity(Intent(this, RegistrarActivity::class.java))
        }

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
                val recordar = binding.chBoxRecordarme.isChecked
                guardarPreferenciaRecordarme(recordar)
                if (!recordar) {
                    auth.signOut()
                }
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                irAMainActivity()
            } else {
                Toast.makeText(this, "Error de autenticación: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun iniciarSesionGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthConGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val recordar = binding.chBoxRecordarme.isChecked
                guardarPreferenciaRecordarme(recordar)

                Toast.makeText(this, "Bienvenido, ${user?.displayName}", Toast.LENGTH_SHORT).show()

                if (recordar) {
                    irAMainActivity()
                } else {
                    googleSignInClient.signOut().addOnCompleteListener {
                        auth.signOut()
                        irAMainActivity()
                    }
                }
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
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
