package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class RegistrarActivity : AppCompatActivity() {

    private lateinit var btnRegistrar: Button
    private lateinit var btnIniciaSesion: MaterialButton

    private lateinit var btnRegistrate: MaterialButton
    private lateinit var btnFacebook: MaterialButton
    private lateinit var btnGoogle: MaterialButton



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

        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnIniciaSesion = findViewById(R.id.btnIniciaSesion)
        btnRegistrate = findViewById(R.id.btnRegistrate)
        btnFacebook = findViewById(R.id.btnFacebook)
        btnGoogle = findViewById(R.id.btnGoogle)



        btnIniciaSesion.setOnClickListener {
            val intent= Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
        btnRegistrar.setOnClickListener {
            val intent = Intent(this, RegistroExitosoActivity::class.java)
            startActivity(intent)

        }

        }
}