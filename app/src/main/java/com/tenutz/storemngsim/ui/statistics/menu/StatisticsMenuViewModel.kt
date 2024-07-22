package com.tenutz.storemngsim.ui.statistics.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByMenusResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByTimeResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByMenusResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByTimeResponse
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import com.tenutz.storemngsim.data.datasource.paging.repository.SalesPagingRepository
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.utils.ext.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsMenuViewModel @Inject constructor(
    private val salesPagingRepository: SalesPagingRepository,
    private val storeRepository: StoreRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_REFRESH_MENU_SALES_LIST = 1000
    }

    val empty = MutableLiveData(true)

    private val _menuSalesTotal = MutableLiveData<StatisticsSalesTotalByMenusResponse>()
    val menuSalesTotal: LiveData<StatisticsSalesTotalByMenusResponse> = _menuSalesTotal

    private val _menuSalesList = MutableLiveData<PagingData<MenuSalesList.MenuSales>>()
    val menuSalesList: LiveData<PagingData<MenuSalesList.MenuSales>> = _menuSalesList

    fun setMenuSales(menuSalesTotal: StatisticsSalesTotalByMenusResponse, menuSalesList: PagingData<MenuSalesList.MenuSales>) {
        _menuSalesTotal.value = menuSalesTotal
        _menuSalesList.value = menuSalesList
    }

    fun menuSalesList(
        dateFrom: Date? = null,
        dateTo: Date? = null,
    ) {

        val commonCond = CommonCondition(
            dateFrom = dateFrom ?: today(),
            dateTo = dateTo ?: today().end(),
        )
        storeRepository.statisticsSalesTotalByMenu(commonCond)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                result.fold(
                    onSuccess = { total ->
                        salesPagingRepository.menuSalesList(commonCond)
                            .cachedIn(viewModelScope)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                viewEvent(Pair(EVENT_REFRESH_MENU_SALES_LIST, Pair(total, it)))
                            }) {
                                Logger.e("$it")
                            }
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }) {
                Logger.e("$it")
            }
    }
}