package com.example.elm327.ui_layer.util

import android.content.Context
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setPadding
import com.example.elm327.R
import com.example.elm327.data_layer.UnitOfMeasurement
import com.example.elm327.util.DecodedPidValue
import com.example.elm327.util.elm.ObdPids

class TableConstructor {
    companion object {
        private val weights = listOf(3F, 5F, 5F)

        fun create(table: TableLayout, context: Context, unitOfMeasurement: UnitOfMeasurement, headerOnclickCallback: (() -> Unit)) {
            val header: List<String> = listOf(
                context.resources.getString(R.string.table_pid),
                context.resources.getString(R.string.table_description),
                "${context.resources.getString(R.string.table_value)} (${unitOfMeasurement.label})",
            )

            table.addView(
                createRow(context, weights.sum(), header.zip(weights).toMap(), headerOnclickCallback)
            )
        }

        fun update(
            table: TableLayout,
            context: Context,
            pidValues: Map<ObdPids, DecodedPidValue>,
            unitOfMeasurement: UnitOfMeasurement,
            headerOnclickCallback: (() -> Unit),
            rowOnclickCallback: ((ObdPids) -> Unit)
        ) {
            table.removeViews(1, (table.childCount - 1).coerceAtLeast(0))
            pidValues.forEach { (pid, value) ->
                table.addView(
                    createRow(
                        context,
                        weights.sum(),
                        listOf(
                            pid.pid, pid.descriptionLong, value.valuesAsString(unitOfMeasurement)
                        ).zip(weights).toMap()
                    ) { rowOnclickCallback(pid) }
                )
            }

        }

        private fun createRow(
            context: Context,
            weightSum: Float,
            cells: Map<String, Float>,
            onclickCallback: (() -> Unit)?,
        ): TableRow {
            val row = TableRow(context)
            row.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            row.weightSum = weightSum

            cells.forEach { (text, weight) ->
                row.addView(createTextView(context, text, weight, onclickCallback))
            }

            return row
        }

        private fun createTextView(
            context: Context,
            text: String,
            weight: Float,
            onclickCallback: (() -> Unit)?,
        ): TextView {
            val textView = TextView(context)
            textView.text = text
            textView.layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.MATCH_PARENT,
                weight
            )
            textView.setBackgroundResource(R.drawable.border)
            textView.setPadding(10)
            if (onclickCallback != null) textView.setOnClickListener { onclickCallback() }
            return textView
        }
    }
}