package com.tenutz.storemngsim.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.application.GlobalViewModel
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByMenusTodayResponse
import com.tenutz.storemngsim.data.datasource.sharedpref.Events
import com.tenutz.storemngsim.databinding.FragmentMainV2Binding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.common.PieMarker
import com.tenutz.storemngsim.ui.statistics.time.args.PieChartMarkerData
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainFragment: BaseFragment() {

    private var _binding: FragmentMainV2Binding? = null
    val binding: FragmentMainV2Binding get() = _binding!!

    val vm: MainViewModel by navGraphViewModels(R.id.navigation_main) {
        defaultViewModelProviderFactory
    }

    val adapter: PieMenusAdapter by lazy {
        PieMenusAdapter(mainActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainV2Binding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
        observeData()
    }

    override fun onResume() {
        super.onResume()

        vm.storeMain()
    }

    private fun observeData() {

        vm.storeMain.observe(viewLifecycleOwner) {
            mainActivity().updateDrawerMenu(it)
        }

        vm.pieMenus.observe(viewLifecycleOwner) { response ->
            response.contents.size.takeIf { it > 3 }?.let {
                adapter.updateItems(response.contents.subList(0, 3))
            } ?: kotlin.run {
                adapter.updateItems(response.contents)
            }
            preparePieData(createPieDate(response), binding.pieMainV2)
        }
        Events.existsNewNotification.observe(viewLifecycleOwner) {
            binding.imageMainV2Alarm.setImageDrawable(ContextCompat.getDrawable(mainActivity(), if(it) R.drawable.ic_black_alarm_2 else R.drawable.ic_black_alarm))
        }
    }

    private fun configureChartAppearance(pieChart: PieChart) {

        pieChart.setExtraOffsets(0f,0f, 0f, 0f)
        pieChart.description.isEnabled = false // chart 밑에 description 표시 유무
        pieChart.legend.isEnabled = false
        pieChart.isRotationEnabled = false

        pieChart.transparentCircleRadius = 0f

        pieChart.marker = PieMarker(mainActivity(), R.layout.marker_pie)
    }

    private fun preparePieData(data: PieData, chart: PieChart) {
        chart.data = data
        chart.invalidate()
    }

    private fun createPieDate(item: StatisticsSalesByMenusTodayResponse): PieData {
        val data = PieData()

        val entries = arrayListOf<PieEntry>()

        item.contents.forEachIndexed { index, it ->
            if(index > 5) return@forEachIndexed
            if(index == 5) {
                val subList = item.contents.subList(index, item.contents.size)
                val countTotal = subList.sumOf { it.count.toDouble() }.toInt()
                val amountTotal = subList.sumOf { it.amount.toDouble() }.toInt()
                val ratioTotal = subList.sumOf { it.ratio.toDouble() }.roundToInt().toFloat()
                entries.add(index, PieEntry(
                    ratioTotal,
                    PieChartMarkerData(
                        "기타",
                        amountTotal,
                        countTotal,
                        ratioTotal,
                    )
                ))
                return@forEachIndexed
            }
            entries.add(index, PieEntry(it.ratio.roundToInt().toFloat(), PieChartMarkerData(it.menuName, it.amount, it.count, it.ratio)))
        }

        val pieDataSet = PieDataSet(entries, "총계")

        data.addDataSet(pieDataSet)
        data.setValueTypeface(ResourcesCompat.getFont(mainActivity(), R.font.ns_bold))
        data.setValueTextColor(ContextCompat.getColor(mainActivity(), R.color.white))
        data.setValueTextSize(14f)
        data.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}%"
            }
        })
        pieDataSet.setColors(
            ContextCompat.getColor(mainActivity(), R.color.rect_4476),
            ContextCompat.getColor(mainActivity(), R.color.rect_4475),
            ContextCompat.getColor(mainActivity(), R.color.rect_4475_a50),
            ContextCompat.getColor(mainActivity(), R.color.rect_4483),
            ContextCompat.getColor(mainActivity(), R.color.rect_4481),
            ContextCompat.getColor(mainActivity(), R.color.rect_4480),
        )

        return data
    }

    private fun initViews() {

        vm.pieMenus()

        binding.listMainV2.adapter = adapter
        configureChartAppearance(binding.pieMainV2)
    }

    private fun setOnClickListeners() {
        binding.imageMainV2Alarm.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNotificationFragment())
        }
        binding.imageMainV2Hamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnMainV2Sales.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNavigationStatistics())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}