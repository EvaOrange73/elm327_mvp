package com.example.elm327.ui_layer.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.elm327.R
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.data_layer.ConnectionState
import com.example.elm327.data_layer.ScanState
import com.example.elm327.data_layer.model.DeviceList
import com.example.elm327.data_layer.model.MacAddress
import com.example.elm327.databinding.FragmentScanBinding
import com.example.elm327.ui_layer.MainActivity
import com.example.elm327.ui_layer.viewModels.ScanFragmentViewModel
import kotlinx.coroutines.launch


class ScanFragment : Fragment() {
    val LOG_TAG = "Scan fragment"

    private var _binding: FragmentScanBinding? = null
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
                    updateSpinner(
                        viewModel.uiState.value.deviceList,
                        viewModel.uiState.value.selectedMacAddress
                    )
                    updateConnectionButton(viewModel.uiState.value.connectionState)
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
        binding.spinner.onItemSelectedListener = itemSelectedListener

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //Scan button

    private fun updateScanButton(scanButtonState: ScanState) {
        val scanButton = binding.button1
        when (scanButtonState) {
            ScanState.NO_PERMISSIONS -> {
                scanButton.setBackgroundColor(Color.RED)
                scanButton.text = getString(R.string.no_scan_permissions)
            }

            ScanState.READY_TO_SCAN -> {
                scanButton.setBackgroundColor(Color.GREEN)
                scanButton.text = getString(R.string.start)
            }

            ScanState.SCANNING -> {
                scanButton.setBackgroundColor(Color.GRAY)
                scanButton.text = getString(R.string.scanning)
            }
        }
    }

    private fun scanButtonOnClick() {
        when (viewModel.uiState.value.scanState) {
            ScanState.NO_PERMISSIONS -> TODO()
            ScanState.READY_TO_SCAN -> (activity as MainActivity).bleBinder.startScan()
            ScanState.SCANNING -> (activity as MainActivity).bleBinder.stopScan()
        }
    }


    // Spinner

    private fun updateSpinner(spinnerList: DeviceList, selectedMacAddress: MacAddress) {
        val spinner = binding.spinner
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            spinnerList.getStringList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        binding.spinner.setSelection(spinnerList.indexOf(selectedMacAddress))
    }

    private var itemSelectedListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val item = parent.getItemAtPosition(position) as String
                (activity as MainActivity).bleBinder.selectMacAddress(MacAddress(item))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


    // Connect Button

    private fun updateConnectionButton(connectionState: ConnectionState) {
        val connectionButton = binding.button2

        when (connectionState) {
            ConnectionState.NO_PERMISSIONS -> {
                connectionButton.setBackgroundColor(Color.RED)
                connectionButton.text = getString(R.string.no_connect_permissions)
            }

            ConnectionState.READY_TO_CONNECT -> {
                connectionButton.setBackgroundColor(Color.GREEN)
                connectionButton.text = getString(R.string.connect)
            }

            ConnectionState.CONNECTING -> {
                connectionButton.setBackgroundColor(Color.GRAY)
                connectionButton.text = getString(R.string.connecting)
            }

            ConnectionState.CONNECTED -> {
                Toast.makeText(context, getString(R.string.success), Toast.LENGTH_SHORT).show()
                connectionButton.setBackgroundColor(Color.GREEN)
                connectionButton.text = getString(R.string.disconnect)
            }

            ConnectionState.FAIL -> {
                Toast.makeText(context, getString(R.string.fail), Toast.LENGTH_SHORT).show()
                connectionButton.setBackgroundColor(Color.GREEN)
                connectionButton.text = getString(R.string.try_again)
            }
        }
    }

    private fun connectButtonOnClick() {
        (activity as MainActivity).bleBinder.connect()
    }
}