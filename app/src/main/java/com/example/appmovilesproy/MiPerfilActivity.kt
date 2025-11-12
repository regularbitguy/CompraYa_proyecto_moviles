package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appmovilesproy.AyudaActivity
import com.example.myproductos.InfoPersonalActivity

class MiPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mi_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun asignarReferencias(){
        val btnInfoPersonal = findViewById<LinearLayout>(R.id.btnInfoPersonal)
        val btnAyuda = findViewById<LinearLayout>(R.id.btnAyuda)

        btnInfoPersonal.setOnClickListener {
            val intent = Intent(this, InfoPersonalActivity::class.java)
            startActivity(intent)
        }
        btnAyuda.setOnClickListener {
            val intent = Intent(this, AyudaActivity::class.java)
            startActivity(intent)
        }
    }
}