package com.tenutz.storemngsim.ui.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.SalesRequest
import com.tenutz.storemngsim.data.datasource.paging.repository.SalesPagingRepository
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.utils.ext.end
import com.tenutz.storemngsim.utils.ext.minusYear
import com.tenutz.storemngsim.utils.ext.today
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val salesPagingRepository: SalesPagingRepository,
    private val storeRepository: StoreRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_REFRESH_SALES_LIST = 1000
    }

    val empty = MutableLiveData(true)

    val conditionsReset = MediatorLiveData<Boolean?>()

    private val _dateFrom = MutableLiveData<Date?>()
    val dateFrom: LiveData<Date?> = _dateFrom

    private val _dateTo = MutableLiveData<Date>(today().end())
    val dateTo: LiveData<Date> = _dateTo

    private val _query = MutableLiveData<String>("")
    val query: LiveData<String> = _query

    private val _paymentType = MutableLiveData<String?>()
    val paymentType: LiveData<String?> = _paymentType

    private val _approvalType = MutableLiveData<String?>()
    val approvalType: LiveData<String?> = _approvalType

    private val _orderType = MutableLiveData<String?>()
    val orderType: LiveData<String?> = _orderType

    init {
        conditionsReset.addSource(_dateFrom) {
            conditionsReset.value = conditionsReset()
        }
        conditionsReset.addSource(_dateTo) {
            conditionsReset.value = conditionsReset()
        }
        conditionsReset.addSource(_query) {
            conditionsReset.value = conditionsReset()
        }
        conditionsReset.addSource(_paymentType) {
            conditionsReset.value = conditionsReset()
        }
        conditionsReset.addSource(_approvalType) {
            conditionsReset.value = conditionsReset()
        }
        conditionsReset.addSource(_orderType) {
            conditionsReset.value = conditionsReset()
        }
    }

    fun setConditions(
        dateFrom: Date?,
        dateTo: Date?,
        paymentType: String?,
        approvalType: String?,
        orderType: String?,
        searchText: String?,
    ) {
        _dateFrom.value = dateFrom
        dateTo?.let { _dateTo.value = it }
        _query.value = searchText ?: ""
        _paymentType.value = paymentType
        _approvalType.value = approvalType
        _orderType.value = orderType
    }

    /*fun resetConditions() {
        _dateFrom.value = null
        _dateTo.value = null
        _query.value = ""
        _paymentType.value = null
        _approvalType.value = null
        _orderType.value = null
    }*/

    private fun conditionsReset() = _dateFrom.value == null &&
            _dateFrom.value == null &&
            _dateTo.value?.equals(today().end()) == true &&
            _query.value == "" &&
            _paymentType.value == null &&
            _approvalType.value == null &&
            _orderType.value == null

    fun salesList() {
        salesPagingRepository.salesList(
            CommonCondition(
                dateFrom = dateFrom.value ?: today().minusYear(100),
                dateTo = dateTo.value ?: today().end(),
                query = query.value,
            ),
            SalesRequest(
                paymentType = paymentType.value,
                approvalType = approvalType.value,
                orderType = orderType.value,
            )
        )
            .cachedIn(viewModelScope)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewEvent(Pair(EVENT_REFRESH_SALES_LIST, it))
            }) {
                Logger.e("$it")
            }.addTo(compositeDisposable)
    }
}