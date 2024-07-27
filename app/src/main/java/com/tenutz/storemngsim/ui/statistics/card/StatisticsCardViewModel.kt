package com.tenutz.storemngsim.ui.statistics.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByCreditCardResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByCreditCardResponse
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.utils.ext.end
import com.tenutz.storemngsim.utils.ext.today
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class StatisticsCardViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_REFRESH_CARD_SALES_LIST = 1000
    }

    private val _cardSalesTotal = MutableLiveData<StatisticsSalesTotalByCreditCardResponse>()
    val cardSalesTotal: LiveData<StatisticsSalesTotalByCreditCardResponse> = _cardSalesTotal

    private val _cardSalesList = MutableLiveData<StatisticsSalesByCreditCardResponse>()
    val cardSalesList: LiveData<StatisticsSalesByCreditCardResponse> = _cardSalesList

    fun setCardSales(cardSalesTotal: StatisticsSalesTotalByCreditCardResponse, cardSalesList: StatisticsSalesByCreditCardResponse) {
        _cardSalesTotal.value = cardSalesTotal
        _cardSalesList.value = cardSalesList
    }

    fun cardSalesList(
        dateFrom: Date? = null,
        dateTo: Date? = null,
    ) {

        val commonCond = CommonCondition(
            dateFrom = dateFrom ?: today(),
            dateTo = dateTo ?: today().end(),
        )
        storeRepository.statisticsSalesTotalByCreditCard(commonCond)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = { total ->
                        storeRepository.statisticsSalesByCreditCard(commonCond)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { result ->
                                result.fold(
                                    onSuccess = {
                                        viewEvent(Pair(EVENT_REFRESH_CARD_SALES_LIST, Pair(total, it)))
                                    },
                                    onFailure = {
                                        Logger.e("$it")
                                    },
                                )
                            }.addTo(compositeDisposable)
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}