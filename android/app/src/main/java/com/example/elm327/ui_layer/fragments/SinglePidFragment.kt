package com.example.elm327.ui_layer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.elm327.databinding.FragmentSinglePidBinding

class SinglePidFragment: Fragment(){

    private var _binding: FragmentSinglePidBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSinglePidBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.pid.text = arguments?.getString("pid") ?: "no pid passed"

        return root
    }
}

