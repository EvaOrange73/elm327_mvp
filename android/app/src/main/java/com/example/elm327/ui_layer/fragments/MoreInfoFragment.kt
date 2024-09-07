package com.example.elm327.ui_layer.fragments

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
import com.example.elm327.databinding.FragmentMoreInfoBinding
import com.example.elm327.ui_layer.util.TableConstructor
import com.example.elm327.ui_layer.viewModels.MoreInfoFragmentViewModel
import com.example.elm327.util.DecodedPidValue
import com.example.elm327.util.elm.ObdPids
import com.example.elm327.util.value.Value
import kotlinx.coroutines.launch

class MoreInfoFragment : Fragment() {

    private var _binding: FragmentMoreInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val bleRepository: BleRepositoryImp by lazy {
        BleRepositoryImp.getInstance()
    }

    private val viewModel: MoreInfoFragmentViewModel by lazy {
        val factory = MoreInfoFragmentViewModel.Factory(bleRepository = bleRepository)
        ViewModelProviders.of(this, factory)[MoreInfoFragmentViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
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
        _binding = FragmentMoreInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        updateTableHeader()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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
            TableConstructor.update(binding.table, it, pidValues, viewModel.uiState.value.unitOfMeasurement, valueOnClickCallback, navigateToSinglePidFragment)
        }
    }

    private val navigateToSinglePidFragment: ((ObdPids) -> Unit) = { pid ->
        val bundle = Bundle()
        bundle.putString("pid", pid.pid)
        val navController = findNavController()
        navController.navigate(R.id.action_nav_more_info_to_nav_single_pid, bundle)
    }
}