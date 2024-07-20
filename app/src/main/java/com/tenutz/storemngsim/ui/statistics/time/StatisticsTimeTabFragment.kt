package com.tenutz.storemngsim.ui.statistics.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByTimeResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByTimeResponse
import com.tenutz.storemngsim.databinding.TabStatisticsTimeBinding
import com.tenutz.storemngsim.ui.common.ChartMarker
import com.tenutz.storemngsim.ui.statistics.StatisticsViewModel
import com.tenutz.storemngsim.utils.ext.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class StatisticsTimeTabFragment : Fragment() {

    private var _binding: TabStatisticsTimeBinding? = null
    val binding: TabStatisticsTimeBinding get() = _binding!!

    val vm: StatisticsTimeViewModel by viewModels()

    private val pVm: StatisticsViewModel by navGraphViewModels(R.id.navigation_statistics) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.timeSalesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabStatisticsTimeBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureChartAppearance(binding.chartTstatisticsTime)

        vm.timeSalesList.observe(viewLifecycleOwner) {

            Logger.i("${it}")

            var contents = it.contents

            when(vm.timeButton.value) {
                R.id.radio_tstatistics_time_all -> {
                }
                R.id.radio_tstatistics_time_card -> {
                    contents = it.contents.map {
                        StatisticsSalesByTimeResponse.StatisticsSalesByTime(
                            it.dateHour,
                            it.creditCardSalesAmount - it.creditCardSalesCancelAmount,
                            it.creditCardSalesCount - it.creditCardSalesCancelCount,
                            0,
                            0,
                            0,
                            0,
                            it.creditCardSalesAmount,
                            it.creditCardSalesCount,
                            it.creditCardSalesCancelAmount,
                            it.creditCardSalesCancelCount,
                        )
                    }.toList()
                }
                R.id.radio_tstatistics_time_cash -> {
                    contents = it.contents.map {
                        StatisticsSalesByTimeResponse.StatisticsSalesByTime(
                            it.dateHour,
                            it.cashSalesAmount - it.cashSalesCancelAmount,
                            it.cashSalesCount - it.cashSalesCancelCount,
                            it.cashSalesAmount,
                            it.cashSalesCount,
                            it.cashSalesCancelAmount,
                            it.cashSalesCancelCount,
                            0,
                            0,
                            0,
                            0,
                        )
                    }.toList()
                }
            }

            when(pVm.timeButton.value) {
                R.id.radio_statistics_time_main_morning -> {
                    contents = contents.filter { it.dateHour?.hour() in 0..11 }
                }
                R.id.radio_statistics_time_main_afternoon -> {
                    contents = contents.filter { it.dateHour?.hour() in 12..23 }
                }
                R.id.radio_statistics_time_main_day -> {
                    contents = contents.filter { it.dateHour?.hour() in 0..23 }
                }
            }

            val minY =
                (contents.maxOfOrNull { it.cashSalesCancelAmount + it.creditCardSalesCancelAmount }
                    ?.toFloat()?.div(10000) ?: 50f)
            val maxY =
                contents.maxOfOrNull { it.cashSalesAmount + it.creditCardSalesAmount }
                    ?.toFloat()?.div(10000) ?: 100f

            updateYAxis(
                binding.chartTstatisticsTime,
                -(minY + (50 - (minY % 51))),
                maxY + (50 - (maxY % 51)),
            )

            val combinedData = CombinedData()

            combinedData.setData(createLineData(StatisticsSalesByTimeResponse(contents)))
            combinedData.setData(createBarData(StatisticsSalesByTimeResponse(contents)))
            prepareChartData(combinedData, binding.chartTstatisticsTime)

            pVm.setTimeTotal(contents.sumOf { it.salesAmount }, contents.sumOf { it.salesCount })
        }

        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    StatisticsTimeViewModel.EVENT_REFRESH_TIME_SALES -> {

                        val args =
                            it.second as Pair<StatisticsSalesTotalByTimeResponse, StatisticsSalesByTimeResponse>

                        vm.setTimeSales(args.first, args.second)
                    }
                }
            }
        }

        pVm.timeViewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    StatisticsViewModel.EVENT_REFRESH_STATISTICS_TIME -> {

                        Logger.i("EVENT_REFRESH_STATISTICS_TIME")

                        val dates = it.second as Pair<Date?, Date?>

                        vm.timeSalesList(dates.first, dates.second)
                    }
                }
            }
        }

        pVm.timeButton.observe(viewLifecycleOwner) {
            when(it) {
                R.id.radio_statistics_time_main_morning -> {
                    updateXAxis(binding.chartTstatisticsTime, 0f, 11f, 1f)
                }
                R.id.radio_statistics_time_main_afternoon -> {
                    updateXAxis(binding.chartTstatisticsTime, 12f, 23f, 1f)
                }
                R.id.radio_statistics_time_main_day -> {
                    updateXAxis(binding.chartTstatisticsTime, 0f, 23f, 2f)
                }
            }
            binding.chartTstatisticsTime.fitScreen()
            vm.refreshTimeSalesList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureChartAppearance(combinedChart: CombinedChart) {
        combinedChart.setExtraOffsets(0f,0f, 0f, 0f)
        combinedChart.description.isEnabled = false // chart 밑에 description 표시 유무
        combinedChart.legend.isEnabled = false
        combinedChart.setScaleEnabled(false)
        combinedChart.fitScreen()

        // XAxis (아래쪽) - 선 유무, 사이즈, 색상, 축 위치 설정
        val xAxis = combinedChart.xAxis
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM // x축 데이터 표시 위치
        xAxis.granularity = 1f
        xAxis.textSize = 10f
        xAxis.textColor = ContextCompat.getColor(mainActivity(), R.color.rect_4480)
        xAxis.spaceMin = 1f // Chart 맨 왼쪽 간격 띄우기
        xAxis.spaceMax = 1f // Chart 맨 오른쪽 간격 띄우기
        xAxis.setAvoidFirstLastClipping(true)

        // YAxis(Right) (왼쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        val yAxisLeft = combinedChart.axisLeft
        yAxisLeft.textSize = 10f
        yAxisLeft.textColor = ContextCompat.getColor(mainActivity(), R.color.rect_4480)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.axisLineWidth = 1f
        yAxisLeft.granularity = 50f
        yAxisLeft.gridLineWidth = 1f
        yAxisLeft.gridColor = ContextCompat.getColor(mainActivity(), R.color.rect_4485)

        // YAxis(Left) (오른쪽)
        val yAxis = combinedChart.axisRight
        yAxis.setDrawAxisLine(false)
        yAxis.isEnabled = false

        // XAxis에 원하는 String 설정하기 (날짜)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}시"
            }
        }

        combinedChart.marker = ChartMarker(mainActivity(), R.layout.marker)
    }

    private fun updateXAxis(
        combinedChart: CombinedChart,
        xMin: Float,
        xMax: Float,
        granularity: Float,
    ) {

        val xAxis = combinedChart.xAxis
        xAxis.axisMinimum = xMin
        xAxis.axisMaximum = xMax
        xAxis.granularity = granularity
        xAxis.setLabelCount(12, true)
    }

    private fun updateYAxis(
        combinedChart: CombinedChart,
        yMin: Float,
        yMax: Float,
    ) {

        val yAxisLeft = combinedChart.axisLeft
        yAxisLeft.axisMinimum = yMin // 최솟값
        yAxisLeft.axisMaximum = yMax // 최댓값

        val yAxis = combinedChart.axisRight
        yAxis.axisMinimum = yMin // 최솟값
        yAxis.axisMaximum = yMax // 최댓값
    }

    private fun createLineData(item: StatisticsSalesByTimeResponse): LineData {
        val entry1: ArrayList<Entry> = ArrayList()
        val chartData = LineData()

        (0..23).forEach { index ->
            if(item.contents.none { it.dateHour?.hour() == index }) {
                entry1.add(Entry(index.toFloat(), 0f))
            } else {
                item.contents.find { it.dateHour?.hour() == index }?.let {
                    entry1.add(Entry(it.dateHour?.hour()?.toFloat() ?: 0f, it.salesAmount / 10000f))
                }
            }
        }

        val dataSet1 = LineDataSet(entry1, "총계")
        chartData.addDataSet(dataSet1)
        dataSet1.lineWidth = 2f
        dataSet1.circleRadius = 5.5f
        dataSet1.circleHoleRadius = 2.5f
        dataSet1.setDrawValues(false)
        dataSet1.setDrawCircleHole(true)
        dataSet1.setDrawCircles(true)
        dataSet1.setDrawHorizontalHighlightIndicator(false)
        dataSet1.setDrawHighlightIndicators(false)
        dataSet1.color = ContextCompat.getColor(mainActivity(), R.color.rect_4476)
        dataSet1.setCircleColor(ContextCompat.getColor(mainActivity(), R.color.rect_4476))

        return chartData
    }

    private fun createBarData(item: StatisticsSalesByTimeResponse): BarData {
        val entry2: ArrayList<BarEntry> = ArrayList()
        val entry3: ArrayList<BarEntry> = ArrayList()
        val chartData = BarData()

        (0..23).forEach { index ->
            if(item.contents.none { it.dateHour?.hour() == index }) {
                entry2.add(BarEntry(index.toFloat(), 0f))
                entry3.add(BarEntry(index.toFloat(), 0f))
            } else {
                item.contents.find { it.dateHour?.hour() == index }?.let {
                    entry2.add(
                        BarEntry(
                            it.dateHour?.hour()?.toFloat() ?: 0f,
                            (it.cashSalesAmount + it.creditCardSalesAmount).div(10000f)
                        )
                    )
                    entry3.add(
                        BarEntry(
                            it.dateHour?.hour()?.toFloat() ?: 0f,
                            -(it.cashSalesCancelAmount + it.creditCardSalesCancelAmount).div(10000f)
                        )
                    )
                }
            }
        }

        val dataSet2 = BarDataSet(entry2, "승인")
        chartData.addDataSet(dataSet2)
        dataSet2.setDrawValues(false)
        dataSet2.color = ContextCompat.getColor(mainActivity(), R.color.rect_4475)

        val dataSet3 = BarDataSet(entry3, "취소")
        chartData.addDataSet(dataSet3)
        dataSet3.setDrawValues(false)
        dataSet3.color = ContextCompat.getColor(mainActivity(), R.color.rect_4481)

        return chartData
    }

    private fun prepareChartData(data: CombinedData, combinedChart: CombinedChart) {
        combinedChart.data = data
        combinedChart.invalidate()
    }

}