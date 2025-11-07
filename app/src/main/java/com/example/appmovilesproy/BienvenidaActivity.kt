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

        btnContinuar.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        // 🔹 Verificar si el usuario ya está logueado y desea ser recordado
        val prefs = getSharedPreferences("userPrefs", MODE_PRIVATE)
        val recordar = prefs.getBoolean("recordarme", false)
        val user = FirebaseAuth.getInstance().currentUser

        // 🔹 Retraso leve para permitir que la UI se vea (opcional)
        Handler(Looper.getMainLooper()).postDelayed({
            if (user != null && recordar) {
                // Usuario logueado + Recordarme activado → Ir al MainActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                // No logueado o sin recordar → Mantener flujo normal
                // (es decir, se queda en la pantalla de bienvenida)
            }
        }, 1000) // 1 segundo opcional de "splash"
    }
}