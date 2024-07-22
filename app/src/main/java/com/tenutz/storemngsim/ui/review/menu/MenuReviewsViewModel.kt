package com.tenutz.storemngsim.ui.review.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.paging.repository.ReviewPagingRepository
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MenuReviewsViewModel @Inject constructor(
    private val reviewPagingRepository: ReviewPagingRepository,
    private val storeRepository: StoreRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_REFRESH_MENU_REVIEWS = 1000
    }

    val empty = MutableLiveData(true)

    private val query = MutableLiveData("")

    private val _queryType = MutableLiveData<Pair<Int?, String?>>(Pair(R.id.radio_tmenu_reviews_all, null))
    val queryType: LiveData<Pair<Int?, String?>> = _queryType

    fun setQueryType(queryType: Pair<Int?, String?>) {
        _queryType.value = queryType
    }

    fun menuReviews(searchText: String? = null) {
        searchText?.let { query.value = it }
        reviewPagingRepository.menuReviews(CommonCondition(query = query.value, queryType = queryType.value?.second))
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                viewEvent(Pair(EVENT_REFRESH_MENU_REVIEWS, it))
            }) {
                Logger.e("$it")
            }.addTo(compositeDisposable)
    }

    fun deleteMenuReviewReply(replySeq: Long, callback: () -> Unit) {
        storeRepository.deleteMenuReviewReply(replySeq)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}