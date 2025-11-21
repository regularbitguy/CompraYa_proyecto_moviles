package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class BienvenidaActivity : AppCompatActivity() {

    private lateinit var btnContinuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        btnContinuar = findViewById(R.id.btnContinuar)

        // Botón normal para nuevos usuarios
        btnContinuar.setOnClickListener {
            startActivity(Intent(this, RegistrarActivity::class.java))
        }

        // 🔹 Verificar si hay sesión activa y "recordarme" está marcado
        val prefs = getSharedPreferences("userPrefs", MODE_PRIVATE)
        val recordar = prefs.getBoolean("recordarme", false)
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null && recordar) {
            // Mostrar una pantalla de carga tipo Splash (por ejemplo, 1.5 segundos)
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }, 1500)
        }
    }
}
