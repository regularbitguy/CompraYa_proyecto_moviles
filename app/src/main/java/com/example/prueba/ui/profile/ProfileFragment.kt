package com.example.prueba.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.example.appmovilesproy.InfoPersonalFragment
import com.example.appmovilesproy.IniciarSesionActivity
import com.example.appmovilesproy.R
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)
        val btnInfoPersonal = view.findViewById<LinearLayout>(R.id.btnInfoPersonal)

        btnInfoPersonal.setOnClickListener {
            val fragment = InfoPersonalFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }
        return view
    }

    private fun cerrarSesion() {

        FirebaseAuth.getInstance().signOut()

        val sharedPref = requireActivity().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }

        val intent = Intent(requireContext(), IniciarSesionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        requireActivity().finish()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
