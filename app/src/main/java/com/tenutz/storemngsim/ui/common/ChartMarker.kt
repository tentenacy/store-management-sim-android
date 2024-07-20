package com.tenutz.storemngsim.ui.common

import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.utils.MPPointF

import android.content.Context

import android.widget.TextView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.tenutz.storemngsim.utils.ext.toCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartMarker(context: Context?, layoutResource: Int): MarkerView(context, layoutResource) {

    private val textMarkerHour: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_hour)
    private val textMarkerSalesAmountTotal: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_sales_amount_total)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        textMarkerHour.text = "${e?.x?.toInt()}시"
        textMarkerSalesAmountTotal.text = "${e?.y?.times(10000f)?.toInt()?.toCurrency}원"
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

}