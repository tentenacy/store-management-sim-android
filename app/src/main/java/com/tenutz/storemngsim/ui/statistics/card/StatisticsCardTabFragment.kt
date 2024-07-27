package com.tenutz.storemngsim.ui.statistics.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByCreditCardResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByCreditCardResponse
import com.tenutz.storemngsim.databinding.TabStatisticsCardBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.statistics.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class StatisticsCardTabFragment: BaseFragment() {

    private var _binding: TabStatisticsCardBinding? = null
    val binding: TabStatisticsCardBinding get() = _binding!!

    val vm: StatisticsCardViewModel by viewModels()

    private val pVm: StatisticsViewModel by navGraphViewModels(R.id.navigation_statistics) {
        defaultViewModelProviderFactory
    }

    val adapter: StatisticsCardAdapter by lazy {
        StatisticsCardAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.cardSalesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabStatisticsCardBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
    }

    private fun initViews() {
        binding.recyclerTstatisticsCard.adapter = adapter
    }

    private fun observeData() {
        vm.cardSalesTotal.observe(viewLifecycleOwner) {
            pVm.setCardTotal(it.salesAmountTotal, it.salesCountTotal)
        }
        vm.cardSalesList.observe(viewLifecycleOwner) {
            adapter.updateAllItems(it.contents)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    StatisticsCardViewModel.EVENT_REFRESH_CARD_SALES_LIST -> {

                        val args =
                            it.second as Pair<StatisticsSalesTotalByCreditCardResponse, StatisticsSalesByCreditCardResponse>

                        vm.setCardSales(args.first, args.second)
                    }
                }
            }
        }
        pVm.cardViewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    StatisticsViewModel.EVENT_REFRESH_STATISTICS_CARD -> {

                        Logger.i("EVENT_REFRESH_STATISTICS_CARD")

                        val dates = it.second as Pair<Date?, Date?>

                        vm.cardSalesList(dates.first, dates.second)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}