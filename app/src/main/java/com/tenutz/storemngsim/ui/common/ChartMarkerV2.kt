package com.tenutz.storemngsim.ui.common

import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.utils.MPPointF

import android.content.Context

import android.widget.TextView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.tenutz.storemngsim.ui.common.args.ChartMakerV2Data
import com.tenutz.storemngsim.utils.ext.toCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartMarkerV2(context: Context?, layoutResource: Int): MarkerView(context, layoutResource) {

    private val textMarkerHour: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_v2_hour)
    private val textMarkerSalesAmount: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_v2_sales_amount)
    private val textMarkerSalesCancelAmount: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_v2_sales_cancel_amount)
    private val textMarkerSalesAmountTotal: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_v2_sales_amount_total)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.data.takeIf { it is ChartMakerV2Data }?.let {
            val chartMakerV2Data = it as ChartMakerV2Data
            textMarkerSalesAmount.text = "${chartMakerV2Data.salesAmount.times(10000f)?.toInt()?.toCurrency}원"
            textMarkerSalesCancelAmount.text = "${chartMakerV2Data.salesCancelAmount.times(10000f)?.toInt()?.toCurrency}원"
            textMarkerSalesAmountTotal.text = "${chartMakerV2Data.salesAmountTotal.times(10000f)?.toInt()?.toCurrency}원"
        }
        textMarkerHour.text = "${e?.x?.toInt()}시"
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

}