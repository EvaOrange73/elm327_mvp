package com.example.elm327.ui_layer.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.elm327.R
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.data_layer.SyncState
import com.example.elm327.databinding.FragmentCarInfoBinding
import com.example.elm327.ui_layer.util.TableConstructor
import com.example.elm327.ui_layer.viewModels.CarInfoFragmentViewModel
import com.example.elm327.util.DecodedPidValue
import com.example.elm327.util.elm.ObdPids
import kotlinx.coroutines.launch

class CarInfoFragment : Fragment() {

    private var _binding: FragmentCarInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val bleRepository: BleRepositoryImp by lazy {
        BleRepositoryImp.getInstance()
    }

    private val viewModel: CarInfoFragmentViewModel by lazy {
        val factory = CarInfoFragmentViewModel.Factory(bleRepository = bleRepository)
        ViewModelProviders.of(this, factory)[CarInfoFragmentViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateCarId(viewModel.uiState.value.carId)
                    updateSyncButton(viewModel.uiState.value.syncButtonState)
                    updateAvailablePids(viewModel.uiState.value.pidGetters)
                    updateTable(viewModel.uiState.value.pidValues)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.syncButton.setOnClickListener { syncButtonOnClick() }
        binding.moreButton.setOnClickListener { navigateToMoreInfo() }
        updateTableHeader()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // editText

    private fun updateCarId(carId: String?) {
        if (viewModel.uiState.value.carId != null) {
            binding.editTextText2.setText(carId)
        }
    }


    // syncButton

    private fun updateSyncButton(syncState: SyncState) {
        val syncButton = binding.syncButton
        when (syncState) {
            SyncState.NO_PERMISSIONS -> {
                syncButton.text = getString(R.string.no_internet_permissions)
                syncButton.setBackgroundColor(Color.RED)
            }

            SyncState.SYNCHRONIZED -> {
                syncButton.text = getString(R.string.sync)
                syncButton.setBackgroundColor(Color.GRAY)
            }

            SyncState.NOT_SYNCHRONIZED -> {
                syncButton.text = getString(R.string.not_sync)
                syncButton.setBackgroundColor(Color.GREEN)
            }
        }
    }

    private fun syncButtonOnClick() {
        if (viewModel.uiState.value.syncButtonState == SyncState.SYNCHRONIZED){
            bleRepository.updateCarId(null)
        } else {
            binding.editTextText2.clearFocus()
            val text = binding.editTextText2.text.toString()
            if (text.isNotEmpty()) {
                bleRepository.updateCarId(text)
            }
        }
    }

    private fun navigateToMoreInfo() {
        val navController = findNavController()
        navController.navigate(R.id.action_nav_gallery_to_nav_slideshow)
    }

    // available pids
    private fun updateAvailablePids(pidGetters: Map<ObdPids, DecodedPidValue>) {
        val textView = binding.availablePids
        var availablePids = ""
        pidGetters.forEach { (key, value) -> availablePids += value.valuesAsPidGetter() }
        textView.text = availablePids
    }

    // table

    private val valueOnClickCallback = {
        bleRepository.setNextUnitOfMeasurement()
        updateTableHeader()
        updateTable(viewModel.uiState.value.pidValues)
    }

    private fun updateTableHeader(){
        binding.table.removeAllViews()
        context?.let {
            TableConstructor.create(binding.table,
                it, viewModel.uiState.value.unitOfMeasurement, valueOnClickCallback)
        }
    }

    private fun updateTable(pidValues: Map<ObdPids, DecodedPidValue>) {
        context?.let {
            TableConstructor.update(
                binding.table,
                it,
                pidValues,
                viewModel.uiState.value.unitOfMeasurement,
                valueOnClickCallback
            ) {}
        }
    }
}