package com.example.elm327.ui_layer.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.elm327.R
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.data_layer.ScanState
import com.example.elm327.data_layer.model.Device
import com.example.elm327.databinding.FragmentScanBinding
import com.example.elm327.ui_layer.MainActivity
import com.example.elm327.ui_layer.viewModels.ScanFragmentViewModel
import kotlinx.coroutines.launch


class ScanFragment : Fragment() {
    val LOG_TAG = "Scan fragment"

    private var _binding: FragmentScanBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val bleRepository: BleRepositoryImp by lazy {
        BleRepositoryImp.getInstance()
    }

    private val viewModel: ScanFragmentViewModel by lazy {
        val factory = ScanFragmentViewModel.Factory(bleRepository = bleRepository)
        ViewModelProviders.of(this, factory)[ScanFragmentViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateScanButton(viewModel.uiState.value.scanState)
                    updateSpinner(viewModel.uiState.value.deviceList)
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

    private fun scanButtonOnClick() {
        when (viewModel.uiState.value.scanState) {
            ScanState.NO_PERMISSIONS -> TODO()
            ScanState.READY_TO_SCAN -> (activity as MainActivity).startScan()
            ScanState.SCANNING -> (activity as MainActivity).stopScan()
        }
    }

    private fun connectButtonOnClick() {
        Log.i(LOG_TAG, "connect button clicked")
    }


    private fun updateScanButton(scanButtonState: ScanState){
        val scanButton = binding.button1
        when (scanButtonState) {
            ScanState.NO_PERMISSIONS -> {
                scanButton.setBackgroundColor(Color.GRAY)
                scanButton.text =  getString(R.string.start)
            }
            ScanState.READY_TO_SCAN -> {
                scanButton.setBackgroundColor(Color.GREEN)
                scanButton.text =  getString(R.string.start)
            }
            ScanState.SCANNING -> {
                scanButton.setBackgroundColor(Color.GRAY)
                scanButton.text =  getString(R.string.scanning)
            }
        }
    }

    private fun updateSpinner(spinnerList: List<Device>) {
        val spinner = binding.spinner
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            spinnerList.map { it.address.toString() })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}