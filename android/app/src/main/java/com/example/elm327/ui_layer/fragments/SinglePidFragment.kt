package com.example.elm327.ui_layer.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.androidplot.xy.CatmullRomInterpolator
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYSeries
import com.example.elm327.R
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.databinding.FragmentSinglePidBinding
import com.example.elm327.ui_layer.viewModels.SinglePidFragmentViewModel
import com.example.elm327.util.DecodedPidValue
import kotlinx.coroutines.launch
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.Arrays


class SinglePidFragment : Fragment() {

    private var _binding: FragmentSinglePidBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val bleRepository: BleRepositoryImp by lazy {
        BleRepositoryImp.getInstance()
    }

    private val viewModel: SinglePidFragmentViewModel by lazy {
        val factory = SinglePidFragmentViewModel.Factory(bleRepository = bleRepository)
        ViewModelProviders.of(this, factory)[SinglePidFragmentViewModel::class.java]
    }


    private val series1Format by lazy {
        LineAndPointFormatter(context, R.xml.line_point_formatter_with_labels).also {
            it.interpolationParams =
                CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updatePlot(viewModel.uiState.value.values)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSinglePidBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.pid.text = arguments?.getString("pid") ?: "no pid passed"

        return root
    }

    private fun formatDomainLabels(domainLabels: Array<Number>): Format {
        return object : Format() {
            override fun format(
                obj: Any,
                toAppendTo: StringBuffer,
                pos: FieldPosition?
            ): StringBuffer {
                val i = Math.round((obj as Number).toFloat())
                return toAppendTo.append(domainLabels[i])
            }

            override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                return null
            }
        }
    }

    private fun updatePlot(values: List<DecodedPidValue>) {
        val (domainLabels: Array<Number>, seriesNumbers: List<Number>) = getPoints(values)
        if (seriesNumbers.size > 3) {
            val plot = binding.plot
            plot.clear()
            plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format =
                formatDomainLabels(domainLabels)
            val series: XYSeries = SimpleXYSeries(
                seriesNumbers, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series"
            )
            plot.addSeries(series, series1Format)
        }
    }

    private fun getPoints(values: List<DecodedPidValue>): Pair<Array<Number>, List<Number>> {
//        val domainLabels = mutableListOf<Number>()
                val domainLabels = arrayOf<Number>(1725751811909, 1725751811909, 1725751812604, 1725751812604)

        val seriesNumbers= mutableListOf<Number>()
//                val seriesNumbers = listOf<Number>(16.991666666666667, 16.991666666666667, 16.958333333333332, 16.958333333333332)


        values.takeLast(4).forEach {
//            domainLabels.add(it.timestamp)
            seriesNumbers.add(it.values[0].value as Number)
        }

        Log.i("AAA", domainLabels.toString())
        Log.i("AAA", seriesNumbers.toString())

        return Pair(domainLabels, seriesNumbers)

//
//        Log.i("AAA", domainLabels.toString())
//        Log.i("AAA", seriesNumbers.toString())
//
//        return Pair(domainLabels, seriesNumbers)
    }
}

