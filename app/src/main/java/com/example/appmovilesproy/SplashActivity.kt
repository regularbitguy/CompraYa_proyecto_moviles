package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Simulamos un breve tiempo de carga (2 segundos)
        Handler(Looper.getMainLooper()).postDelayed({
            checkUserSession()
        }, 2000)
    }

    private fun checkUserSession() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val rememberMe = prefs.getBoolean("rememberMe", false)

        if (user != null && rememberMe) {
            // Usuario autenticado y eligió recordarse → va al MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // Usuario no logeado o no eligió recordarse → va al BienvenidaActivity
            startActivity(Intent(this, BienvenidaActivity::class.java))
        }
        finish()
    }
}
