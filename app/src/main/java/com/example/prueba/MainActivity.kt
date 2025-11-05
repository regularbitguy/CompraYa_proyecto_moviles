package com.example.prueba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.prueba.databinding.ActivityMainBinding
import com.example.prueba.ui.home.HomeFragment
import com.example.prueba.ui.profile.ProfileFragment
import com.example.prueba.ui.publications.PublicationsFragment
import com.example.prueba.ui.publish.PublishFragment
import com.example.prueba.ui.wishlist.WishlistFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        val selectedTab = intent?.getStringExtra("selected_tab")
        when (selectedTab) {
            "inicio" -> {
                binding.bottomNavigation.selectedItemId = R.id.nav_inicio
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
            }
            "deseados" -> {
                binding.bottomNavigation.selectedItemId = R.id.nav_deseados
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WishlistFragment()).commit()
            }
            "publicar" -> {
                binding.bottomNavigation.selectedItemId = R.id.nav_publicar
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PublishFragment()).commit()
            }
            "publicaciones" -> {
                binding.bottomNavigation.selectedItemId = R.id.nav_notificaciones
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PublicationsFragment()).commit()
            }
            "perfil" -> {
                binding.bottomNavigation.selectedItemId = R.id.nav_perfil
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
            }
        }

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
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
