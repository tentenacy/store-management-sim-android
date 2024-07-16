package com.tenutz.storemngsim.ui.review

import com.tenutz.storemngsim.data.datasource.paging.repository.ReviewPagingRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewPagingRepository: ReviewPagingRepository,
): BaseViewModel() {

}