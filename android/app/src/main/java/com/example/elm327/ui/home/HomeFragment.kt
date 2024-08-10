package com.example.elm327.ui.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.elm327.databinding.FragmentHomeBinding


class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }



//        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
//        val button = view.findViewById<View>(R.id.start_button) as Button
//        button.setOnClickListener(this)

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onClick(v: View?) {
//        when (v!!.id) {
//            R.id.start_button -> {
//                Toast.makeText(activity, "button 3 clicked", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}