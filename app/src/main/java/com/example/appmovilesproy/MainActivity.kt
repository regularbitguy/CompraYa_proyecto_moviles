package com.example.appmovilesproy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appmovilesproy.databinding.ActivityMainBinding
// 💡 Asegúrate de importar los fragmentos desde el paquete correcto de tu proyecto
import com.example.prueba.ui.home.HomeFragment
import com.example.prueba.ui.profile.ProfileFragment
import com.example.prueba.ui.publications.PublicationsFragment
import com.example.prueba.ui.publish.PublishFragment
import com.example.prueba.ui.wishlist.WishlistFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        // --- INICIO DE LA LÓGICA SIMPLIFICADA ---

        // 1. ÚNICA VERIFICACIÓN: Si por alguna razón se llega aquí sin un usuario,
        //    lo redirigimos a la pantalla de login como medida de seguridad.
        if (auth.currentUser == null) {
            irAIniciarSesion()
            return // Detiene la ejecución para no inflar la vista innecesariamente
        }

        // 2. Si hay un usuario, inflamos la vista y configuramos la UI.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar fragmento inicial (Home)
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Control de navegación inferior
        setupBottomNavigation()

        // --- FIN DE LA LÓGICA SIMPLIFICADA ---
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> { replaceFragment(HomeFragment()); true }
                R.id.nav_deseados -> { replaceFragment(WishlistFragment()); true }
                R.id.nav_publicar -> { replaceFragment(PublishFragment()); true }
                R.id.nav_notificaciones -> { replaceFragment(PublicationsFragment()); true }
                R.id.nav_perfil -> { replaceFragment(ProfileFragment()); true }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    private fun irAIniciarSesion() {
        val intent = Intent(this, IniciarSesionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}