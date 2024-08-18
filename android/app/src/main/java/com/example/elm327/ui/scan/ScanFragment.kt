package com.example.elm327.ui.scan

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.elm327.MainActivity
import com.example.elm327.R
import com.example.elm327.databinding.FragmentScanBinding
import kotlinx.coroutines.launch


class ScanFragment : Fragment() {
    val LOG_TAG = "Scan fragment"

    private var _binding: FragmentScanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ScanViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateScanButton(viewModel.uiState.value.scanButtonState)
                    updateSpinner(viewModel.uiState.value.spinnerList)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.button1.setOnClickListener { scanButtonOnClick() }
        binding.button2.setOnClickListener { connectButtonOnClick() }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun scanButtonOnClick(){
        Log.i(LOG_TAG, "scan button clicked")
        val viewModel: ScanViewModel by viewModels()
        (activity as MainActivity).startScan()
        viewModel.startScan()
    }

    private fun connectButtonOnClick(){
        Log.i(LOG_TAG, "connect button clicked")
    }


    private fun updateScanButton(scanButtonState: ScanButtonState){
        val scanButton = binding.button1
        when (scanButtonState) {
            ScanButtonState.NO_PERMISSIONS -> {
                scanButton.setBackgroundColor(Color.GRAY)
                scanButton.text =  getString(R.string.start)
            }
            ScanButtonState.READY_TO_SCAN -> {
                scanButton.setBackgroundColor(Color.GREEN)
                scanButton.text =  getString(R.string.start)
            }
            ScanButtonState.SCANNING -> {
                scanButton.setBackgroundColor(Color.GRAY)
                scanButton.text =  getString(R.string.scanning)
            }
        }
    }

    private fun updateSpinner(spinnerList: List<String>){
        if (spinnerList.isEmpty()) {
            Toast.makeText(context, "no devices found", Toast.LENGTH_SHORT).show()
        }
        else {
            val spinner = binding.spinner
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireActivity().applicationContext, android.R.layout.simple_spinner_item, spinnerList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}