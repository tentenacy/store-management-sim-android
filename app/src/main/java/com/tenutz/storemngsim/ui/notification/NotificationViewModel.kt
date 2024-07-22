package com.tenutz.storemngsim.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.paging.repository.PushAlarmPagingRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.review.menu.MenuReviewsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val pushAlarmPagingRepository: PushAlarmPagingRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_REFRESH_NOTIFICATIONS = 1000
    }

    val empty = MutableLiveData(true)

    fun notifications() {
        pushAlarmPagingRepository.pushAlarms(CommonCondition())
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                viewEvent(Pair(EVENT_REFRESH_NOTIFICATIONS, it))
            }) {
                Logger.e("$it")
            }.addTo(compositeDisposable)
    }
}