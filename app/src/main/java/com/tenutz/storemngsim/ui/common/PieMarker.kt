package com.tenutz.storemngsim.ui.common

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.tenutz.storemngsim.ui.statistics.time.args.PieChartMarkerData
import com.tenutz.storemngsim.utils.ext.toCurrency
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class PieMarker(context: Context?, layoutResource: Int): MarkerView(context, layoutResource) {

    private val textMarkerMenuName: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_pie_menu_name)
    private val textMarkerRatio: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_pie_ratio)
    private val textMarkerCount: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_pie_count)
    private val textMarkerAmount: TextView = findViewById<TextView>(com.tenutz.storemngsim.R.id.text_marker_pie_amount)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.data.takeIf { it is PieChartMarkerData }?.let {
            val data = it as PieChartMarkerData
            textMarkerMenuName.text = "${data.menuName}"
            textMarkerRatio.text = "${data.ratio.roundToInt()}%"
            textMarkerCount.text = "${data.count.toCurrency}개"
            textMarkerAmount.text = "${data.amount.toCurrency}원"
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

}