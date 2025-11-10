package com.example.prueba

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appmovilesproy.CategoriaAdapter
import com.example.appmovilesproy.MainActivity
import com.example.appmovilesproy.R
import com.example.appmovilesproy.databinding.ActivityCategoriasBinding
import com.example.prueba.ui.chats.ChatsFragment

class CategoriasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriasBinding
    private lateinit var navIcons: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listaCategorias = listOf(
            Categoria("Videojuegos", R.drawable.img_videojuegos),
            Categoria("Prendas", R.drawable.img_prendas),
            Categoria("Computación", R.drawable.img_computacion),
            Categoria("Accesorios", R.drawable.img_accesorios),
            Categoria("Música", R.drawable.img_musica),
            Categoria("Inmuebles", R.drawable.img_inmuebles),
            Categoria("Salud y Belleza", R.drawable.img_saludbelleza),
            Categoria("Ropa deportiva", R.drawable.img_ropadeportiva),
            Categoria("Calzado deportivo", R.drawable.img_calzadodepor),
            Categoria("Calzado elegante", R.drawable.img_calzadoele),
            Categoria("Perfumería", R.drawable.img_perfumeria),
            Categoria("Utensilios de cocina", R.drawable.img_utensilios),
            Categoria("Limpieza", R.drawable.img_limpieza),
            Categoria("Libros", R.drawable.img_libros),
            Categoria("Otras categorías", R.drawable.img_categorias)
        )

        // Configurar el RecyclerView
        binding.rvCategorias.layoutManager = GridLayoutManager(this, 3)
        binding.rvCategorias.adapter = CategoriaAdapter(listaCategorias)

        // Acciones de los botones superiores
        binding.btnChatCategoria.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.btnChatCategoria, ChatsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnBuscarCategoria.setOnClickListener {
            startActivity(Intent(this, Buscar::class.java))
        }

        // --- Configurar la barra inferior personalizada ---
        navIcons = listOf(
            findViewById(R.id.navInicio),
            findViewById(R.id.navDeseos),
            findViewById(R.id.navAgregar),
            findViewById(R.id.navPublicaciones),
            findViewById(R.id.navPerfil)
        )

        setupBottomBar()
    }

    private fun setupBottomBar() {
        findViewById<ImageView>(R.id.navInicio).setOnClickListener {
            irAMainConTab("inicio")
        }

        findViewById<ImageView>(R.id.navDeseos).setOnClickListener {
            irAMainConTab("deseados")
        }

        findViewById<ImageView>(R.id.navAgregar).setOnClickListener {
            irAMainConTab("publicar")
        }

        findViewById<ImageView>(R.id.navPublicaciones).setOnClickListener {
            irAMainConTab("publicaciones")
        }

        findViewById<ImageView>(R.id.navPerfil).setOnClickListener {
            irAMainConTab("perfil")
        }
    }

    private fun irAMainConTab(tab: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("selected_tab", tab)
        startActivity(intent)
        finish()
    }
}
