package com.example.prueba.ui.publish
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmovilesproy.R
import com.example.appmovilesproy.databinding.FragmentPublishSuccessBinding

class PublishSuccessFragment : Fragment() {

    private var _binding: FragmentPublishSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPublishSuccessBinding.inflate(inflater, container, false)

        binding.btnVolverInicio.setOnClickListener {
            val homeFragment = com.example.prueba.ui.home.HomeFragment()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit()

            requireActivity().supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

