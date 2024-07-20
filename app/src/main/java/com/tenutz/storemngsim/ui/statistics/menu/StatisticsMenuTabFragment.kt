package com.tenutz.storemngsim.ui.statistics.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByMenusResponse
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import com.tenutz.storemngsim.databinding.TabStatisticsMenuBinding
import com.tenutz.storemngsim.ui.statistics.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class StatisticsMenuTabFragment: Fragment() {

    private var _binding: TabStatisticsMenuBinding? = null
    val binding: TabStatisticsMenuBinding get() = _binding!!

    val vm: StatisticsMenuViewModel by viewModels()

    private val pVm: StatisticsViewModel by navGraphViewModels(R.id.navigation_statistics) {
        defaultViewModelProviderFactory
    }

    val adapter: StatisticsMenuAdapter by lazy {
        StatisticsMenuAdapter().apply {
            addLoadStateListener { loadState ->
                binding.textTstatisticsMenuEmpty.isVisible =
                    loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.menuSalesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabStatisticsMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerTstatisticsMenu.adapter = adapter

        vm.menuSalesTotal.observe(viewLifecycleOwner) {
            pVm.setMenuTotal(it.cAuthAmountTotal, it.cAuthCountTotal)
        }

        vm.menuSalesList.observe(viewLifecycleOwner) {
            adapter.submitData(
                viewLifecycleOwner.lifecycle,
                it
            )
        }

        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    StatisticsMenuViewModel.EVENT_REFRESH_MENU_SALES_LIST -> {

                        val args =
                            it.second as Pair<StatisticsSalesTotalByMenusResponse, PagingData<MenuSalesList.MenuSales>>

                        vm.setMenuSales(args.first, args.second)
                    }
                }
            }
        }
        pVm.menuViewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    StatisticsViewModel.EVENT_REFRESH_STATISTICS_MENU -> {

                        Logger.i("EVENT_REFRESH_STATISTICS_MENU")

                        val dates = it.second as Pair<Date?, Date?>

                        vm.menuSalesList(dates.first, dates.second)
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