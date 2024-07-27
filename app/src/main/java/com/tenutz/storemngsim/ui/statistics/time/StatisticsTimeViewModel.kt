package com.tenutz.storemngsim.ui.statistics.time

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByTimeResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByTimeResponse
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.utils.ext.noon
import com.tenutz.storemngsim.utils.ext.today
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class StatisticsTimeViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_REFRESH_TIME_SALES = 1000
    }

    private val _timeSalesTotal = MutableLiveData<StatisticsSalesTotalByTimeResponse>()
    val timeSalesTotal: LiveData<StatisticsSalesTotalByTimeResponse> = _timeSalesTotal

    private val _timeSalesList = MutableLiveData<StatisticsSalesByTimeResponse>()
    val timeSalesList: LiveData<StatisticsSalesByTimeResponse> = _timeSalesList

    private val _timeButton = MutableLiveData<Int>(R.id.radio_tstatistics_time_all)
    val timeButton: LiveData<Int> = _timeButton

    fun setTimeSales(timeSalesTotal: StatisticsSalesTotalByTimeResponse, timeSalesList: StatisticsSalesByTimeResponse) {
        _timeSalesTotal.value = timeSalesTotal
        _timeSalesList.value = timeSalesList
    }

    fun timeSalesList(dateFrom: Date? = null, dateTo: Date? = null) {
        val commonCond = CommonCondition(
            dateFrom = dateFrom ?: today(),
            dateTo = dateTo ?: today().noon(),
        )
        storeRepository.statisticsSalesTotalByTime(commonCond)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = { total ->
                        storeRepository.statisticsSalesByTime(commonCond)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { result ->
                                result.fold(
                                    onSuccess = {
                                        viewEvent(Pair(EVENT_REFRESH_TIME_SALES, Pair(total, it)))
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

    fun setTimeButton(view: View?) {
        _timeButton.value = view?.id
        refreshTimeSalesList()
    }

    fun refreshTimeSalesList() {
        _timeSalesList.value?.let { _timeSalesList.value = _timeSalesList.value }
    }
}
