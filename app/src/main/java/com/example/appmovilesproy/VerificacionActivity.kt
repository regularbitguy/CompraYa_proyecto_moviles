package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VerificacionActivity : AppCompatActivity() {
    private lateinit var codigo1: EditText
    private lateinit var codigo2: EditText
    private lateinit var codigo3: EditText
    private lateinit var codigo4: EditText
    private lateinit var btnVerificar: Button
    private lateinit var txtReenviar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verificacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
        iniciarTemporizador()
    }
    private fun asignarReferencias() {
        codigo1 = findViewById(R.id.codigo1)
        codigo2 = findViewById(R.id.codigo2)
        codigo3 = findViewById(R.id.codigo3)
        codigo4 = findViewById(R.id.codigo4)
        btnVerificar = findViewById(R.id.btnVerificar)
        txtReenviar = findViewById(R.id.txtReenviar)

        // Acción del botón Verificar
        btnVerificar.setOnClickListener {
            val codigo = codigo1.text.toString() + codigo2.text.toString() + codigo3.text.toString() + codigo4.text.toString()
            if (codigo.length == 4) {
                // Lógica para verificar el código ingresado
                verificarCodigo(codigo)

                val intent = Intent(this, CodigoVerificadoActivity::class.java)
                startActivity(intent)
            } else {
                // Mostrar mensaje de error si no se ha ingresado un código completo
                // Puedes usar un Toast o un mensaje de error
                txtReenviar.text = "Por favor ingresa un código completo."
            }
        }
    }

    private fun verificarCodigo(codigo: String) {
        // Aquí podrías hacer la lógica de verificación del código (ej. validación con API)
        // Por ahora, solo mostramos un mensaje de éxito
        txtReenviar.text = "Código verificado exitosamente."
    }

    // Función para iniciar el temporizador del "Reenviar en 30 seg"
    private fun iniciarTemporizador() {
        val timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                txtReenviar.text = "Reenviar en $secondsRemaining seg"
            }

            override fun onFinish() {
                txtReenviar.text = "Reenviar ahora"
            }
        }
        timer.start()
    }
}