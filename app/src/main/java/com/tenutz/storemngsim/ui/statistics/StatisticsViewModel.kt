package com.tenutz.storemngsim.ui.statistics

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.utils.Event
import com.tenutz.storemngsim.utils.ext.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(

): BaseViewModel() {

    companion object {

        const val EVENT_REFRESH_STATISTICS_MENU = 1000
        const val EVENT_REFRESH_STATISTICS_TIME = 1001
        const val EVENT_REFRESH_STATISTICS_CARD = 1002
    }

    private val _menuViewEvent = MutableLiveData<Event<Pair<Int, Any?>>>()
    val menuViewEvent: LiveData<Event<Pair<Int, Any?>>>
        get() = _menuViewEvent

    private val _timeViewEvent = MutableLiveData<Event<Pair<Int, Any?>>>()
    val timeViewEvent: LiveData<Event<Pair<Int, Any?>>>
        get() = _timeViewEvent

    private val _cardViewEvent = MutableLiveData<Event<Pair<Int, Any?>>>()
    val cardViewEvent: LiveData<Event<Pair<Int, Any?>>>
        get() = _cardViewEvent

    private val _menuExpanded = MutableLiveData(true)
    val menuExpanded: LiveData<Boolean> = _menuExpanded

    private val _menuButton = MutableLiveData(R.id.radio_statistics_menu_main_today)
    val menuButton: LiveData<Int> = _menuButton

    private val _menuDateFrom = MutableLiveData(today())
    val menuDateFrom: LiveData<Date> = _menuDateFrom

    private val _menuDateTo = MutableLiveData(today().end())
    val menuDateTo: LiveData<Date> = _menuDateTo

    private val _menuTotalAmount = MutableLiveData(0)
    val menuTotalAmount: LiveData<Int> = _menuTotalAmount

    private val _menuTotalCount = MutableLiveData(0)
    val menuTotalCount: LiveData<Int> = _menuTotalCount

    private val _timeExpanded = MutableLiveData(true)
    val timeExpanded: LiveData<Boolean> = _timeExpanded

    private val _timeButton = MutableLiveData(R.id.radio_statistics_time_main_morning)
    val timeButton: LiveData<Int> = _timeButton

    private val _timeDateFrom = MutableLiveData(today())
    val timeDateFrom: LiveData<Date> = _timeDateFrom

    private val _timeDateTo = MutableLiveData(today().noon())
    val timeDateTo: LiveData<Date> = _timeDateTo

    private val _timeTotalAmount = MutableLiveData(0)
    val timeTotalAmount: LiveData<Int> = _timeTotalAmount

    private val _timeTotalCount = MutableLiveData(0)
    val timeTotalCount: LiveData<Int> = _timeTotalCount

    private val _cardExpanded = MutableLiveData(true)
    val cardExpanded: LiveData<Boolean> = _cardExpanded

    private val _cardButton = MutableLiveData(R.id.radio_statistics_card_main_today)
    val cardButton: LiveData<Int> = _cardButton

    private val _cardDateFrom = MutableLiveData(today())
    val cardDateFrom: LiveData<Date> = _cardDateFrom

    private val _cardDateTo = MutableLiveData(today().end())
    val cardDateTo: LiveData<Date> = _cardDateTo

    private val _cardTotalAmount = MutableLiveData(0)
    val cardTotalAmount: LiveData<Int> = _cardTotalAmount

    private val _cardTotalCount = MutableLiveData(0)
    val cardTotalCount: LiveData<Int> = _cardTotalCount

    fun toggleMenuExpanded() {
        _menuExpanded.value = _menuExpanded.value != true
    }

    fun toggleTimeExpanded() {
        _timeExpanded.value = _timeExpanded.value != true
    }

    fun toggleCardExpanded() {
        _cardExpanded.value = _cardExpanded.value != true
    }

    fun setMenuButton(view: View?) {

        _menuButton.value = view?.id

        view?.id?.let {

            when(it) {
                R.id.radio_statistics_menu_main_today -> {
                    setMenuDate(today(), today().end())
                }
                R.id.radio_statistics_menu_main_week -> {
                    setMenuDate(today().minusDay(7), today().end())
                }
                R.id.radio_statistics_menu_main_month -> {
                    setMenuDate(today().minusMonth(1), today().end())
                }
            }
        }

        _menuViewEvent.postValue(Event(Pair(EVENT_REFRESH_STATISTICS_MENU, Pair(_menuDateFrom.value, _menuDateTo.value))))
    }

    fun setTimeButton(view: View?) {

        view?.id?.let {

            _timeButton.value = it

            when(it) {
                R.id.radio_statistics_time_main_morning -> {
                    setTimeDate(_timeDateFrom.value?.start(), _timeDateTo.value?.noon())
                }
                R.id.radio_statistics_time_main_afternoon -> {
                    setTimeDate(_timeDateFrom.value?.noon(), _timeDateTo.value?.end())
                }
                R.id.radio_statistics_time_main_day -> {
                    setTimeDate(_timeDateFrom.value?.start(), _timeDateTo.value?.end())
                }
            }
        } ?: kotlin.run {
            _timeViewEvent.postValue(Event(Pair(EVENT_REFRESH_STATISTICS_TIME, Pair(_timeDateFrom.value, _timeDateTo.value))))
        }
    }

    fun setCardButton(view: View?) {

        _cardButton.value = view?.id

        view?.id?.let {

            when(it) {
                R.id.radio_statistics_card_main_today -> {
                    setCardDate(today(), today().end())
                }
                R.id.radio_statistics_card_main_week -> {
                    setCardDate(today().minusDay(7), today().end())
                }
                R.id.radio_statistics_card_main_month -> {
                    setCardDate(today().minusMonth(1), today().end())
                }
            }
        }

        _cardViewEvent.postValue(Event(Pair(EVENT_REFRESH_STATISTICS_CARD, Pair(_cardDateFrom.value, _cardDateTo.value))))
    }

    fun setMenuDate(dateFrom: Date? = null, dateTo: Date? = null) {
        dateFrom?.let { _menuDateFrom.value = it }
        dateTo?.let { _menuDateTo.value = it }
    }

    fun setTimeDate(dateFrom: Date? = null, dateTo: Date? = null) {
        dateFrom?.let { _timeDateFrom.value = it }
        dateTo?.let { _timeDateTo.value = it }
    }

    fun setCardDate(dateFrom: Date? = null, dateTo: Date? = null) {
        dateFrom?.let { _cardDateFrom.value = it }
        dateTo?.let { _cardDateTo.value = it }
    }

    fun setMenuTotal(amount: Int, count: Int) {
        _menuTotalAmount.value = amount
        _menuTotalCount.value = count
    }

    fun setTimeTotal(amount: Int, count: Int) {
        _timeTotalAmount.value = amount
        _timeTotalCount.value = count
    }

    fun setCardTotal(amount: Int, count: Int) {
        _cardTotalAmount.value = amount
        _cardTotalCount.value = count
    }

}