package com.tenutz.storemngsim.ui.help

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.repository.help.HelpRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.help.args.HelpsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HelpsViewModel @Inject constructor(
    private val helpRepository: HelpRepository,
): BaseViewModel() {

    private val query = MutableLiveData("")

    private val _helps = MutableLiveData<HelpsArgs>()
    val helps: LiveData<HelpsArgs> = _helps

    private val _expandedItemCount = MutableLiveData(0)
    val expandedItemCount: LiveData<Int> = _expandedItemCount

    fun updateExpandedItemCount() {
        _helps.value?.let {
            _expandedItemCount.value = it.helps.count { it.expanded }
        }
    }

    fun helps(searchText: String? = null) {
        searchText?.let { query.value = it }
        helpRepository.helps(CommonCondition(query = query.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _helps.value = HelpsArgs(
                            it.helps.mapIndexed { index, it ->
                                HelpsArgs.Help(
                                    seq = it.seq,
                                    title = it.title,
                                    content = it.content,
                                    imageName = it.imageName,
                                    imageUrl = it.imageUrl,
                                    createdAt = it.createdAt,
                                    createdBy = it.createdBy,
                                    createdIp = it.createdIp,
                                    updatedAt = it.updatedAt,
                                    updatedBy = it.updatedBy,
                                    updatedIp = it.updatedIp,
                                )
                            }
                        )
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}