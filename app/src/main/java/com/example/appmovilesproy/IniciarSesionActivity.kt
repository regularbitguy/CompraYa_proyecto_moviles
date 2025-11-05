package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.MediaController
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var btnIniciarSesion: Button
    private lateinit var chBoxRecordarme: CheckBox
    private lateinit var txtOlvidastePass: TextView
    private lateinit var btnRegistrate: MaterialButton
    private lateinit var txtCorreo: TextInputEditText
    private lateinit var txtContraseña: TextInputEditText
    private lateinit var txtDNI: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    private fun asignarReferencias(){
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        chBoxRecordarme = findViewById(R.id.chBoxRecordarme)
        txtOlvidastePass= findViewById(R.id.txtOlvidastePass)
        btnRegistrate = findViewById(R.id.btnRegistrate)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtContraseña = findViewById(R.id.txtContraseña)
        txtDNI = findViewById(R.id.txtDNI)


        btnRegistrate.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }
        txtOlvidastePass.setOnClickListener {
            val intent = Intent(this, OlvideContrasenaActivity::class.java)
            startActivity(intent)
        }
        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}