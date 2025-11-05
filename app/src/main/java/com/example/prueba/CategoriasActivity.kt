package com.example.prueba

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.prueba.databinding.ActivityCategoriasBinding
import com.example.prueba.ui.chats.ChatsFragment

class CategoriasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriasBinding

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

        binding.btnChatCategoria.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChatsFragment())
                .addToBackStack(null)
                .commit()
        }


        binding.rvCategorias.layoutManager = GridLayoutManager(this, 3)
        binding.rvCategorias.adapter = CategoriaAdapter(listaCategorias)

        // Opcional: marcar el menú seleccionado si quieres reflejar que estás en Categorías.
        // Si prefieres que ninguna opción esté seleccionada, comenta la siguiente línea.
        binding.bottomNavigationCategorias.menu.findItem(R.id.nav_inicio).isChecked = false

        // Manejo del bottom navigation: lanzamos MainActivity indicando la pestaña que queremos
        binding.bottomNavigationCategorias.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    // Regresa a MainActivity y selecciona la pestaña "Inicio"
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("selected_tab", "inicio")
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_deseados -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("selected_tab", "deseados")
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_publicar -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("selected_tab", "publicar")
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_notificaciones -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("selected_tab", "publicaciones")
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_perfil -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("selected_tab", "perfil")
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        // Botones del header (buscar / chat)
        binding.btnBuscarCategoria.setOnClickListener {
            startActivity(Intent(this, Buscar::class.java))
        }
        binding.btnChatCategoria.setOnClickListener {
            // abrir actividad de chats si tienes una; ejemplo:
            // startActivity(Intent(this, ChatsActivity::class.java))
        }
    }
}
