package com.example.appmovilesproy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appmovilesproy.databinding.ActivityMainBinding
import com.example.prueba.ui.home.HomeFragment
import com.example.prueba.ui.profile.ProfileFragment
import com.example.prueba.ui.publications.PublicationsFragment
import com.example.prueba.ui.publish.PublishFragment
import com.example.prueba.ui.wishlist.WishlistFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var navIcons: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val prefs = getSharedPreferences("userPrefs", MODE_PRIVATE)
        val recordar = prefs.getBoolean("recordarme", false)

        if (auth.currentUser == null || !recordar) {
            auth.signOut()
            irAIniciarSesion()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navIcons = listOf(
            findViewById(R.id.navInicio),
            findViewById(R.id.navDeseos),
            findViewById(R.id.navAgregar),
            findViewById(R.id.navPublicaciones),
            findViewById(R.id.navPerfil)
        )

        // Cargar fragmento inicial
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            setActive(navIcons[0]) // Marcar Inicio como activo
        }

        setupBottomBar()
    }

    private fun setupBottomBar() {
        findViewById<ImageView>(R.id.navInicio).setOnClickListener {
            replaceFragment(HomeFragment())
            setActive(it as ImageView)
        }

        findViewById<ImageView>(R.id.navDeseos).setOnClickListener {
            replaceFragment(WishlistFragment())
            setActive(it as ImageView)
        }

        findViewById<ImageView>(R.id.navAgregar).setOnClickListener {
            replaceFragment(PublishFragment())
            setActive(it as ImageView)
        }

        findViewById<ImageView>(R.id.navPublicaciones).setOnClickListener {
            replaceFragment(PublicationsFragment())
            setActive(it as ImageView)
        }

        findViewById<ImageView>(R.id.navPerfil).setOnClickListener {
            replaceFragment(ProfileFragment())
            setActive(it as ImageView)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun irAIniciarSesion() {
        val intent = Intent(this, IniciarSesionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun setActive(activeIcon: ImageView) {
        navIcons.forEach {
            it.setColorFilter(Color.parseColor("#666666")) // inactivo
        }
        activeIcon.setColorFilter(Color.parseColor("#1E5631")) // activo
    }
}
