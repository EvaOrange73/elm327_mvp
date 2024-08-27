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
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.databinding.FragmentMoreInfoBinding
import com.example.elm327.ui_layer.util.TableConstructor
import com.example.elm327.ui_layer.viewModels.MoreInfoFragmentViewModel
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

        context?.let { TableConstructor.create(binding.table, it) }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateTable(pidValues: Map<ObdPids, List<Value>>) {
        context?.let { TableConstructor.update(binding.table, it, pidValues) }
    }
}