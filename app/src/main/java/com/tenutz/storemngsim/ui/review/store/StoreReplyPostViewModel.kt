package com.tenutz.storemngsim.ui.review.store

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.store.ReviewReplyCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.store.ReviewReplyUpdateRequest
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class StoreReplyPostViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    val content = MutableLiveData("")

    fun createStoreReviewReply(
        reviewSeq: Long,
        request: ReviewReplyCreateRequest,
        callback: () -> Unit,
    ) {
        storeRepository.createStoreReviewReply(reviewSeq, request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        callback()
                        viewEvent(Pair(EVENT_NAVIGATE_UP, Unit))
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }

    fun updateStoreReviewReply(
        replySeq: Long,
        request: ReviewReplyUpdateRequest,
        callback: () -> Unit,
    ) {
        storeRepository.updateStoreReviewReply(replySeq, request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        callback()
                        viewEvent(Pair(EVENT_NAVIGATE_UP, Unit))
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}