package com.tenutz.storemngsim.ui.menu.optionmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esafirm.imagepicker.model.Image
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionCreateRequest
import com.tenutz.storemngsim.data.datasource.api.err.ErrorCode
import com.tenutz.storemngsim.data.repository.option.OptionRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.utils.ext.toErrorResponseOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OptionMenuAddViewModel @Inject constructor(
    private val optionMenuRepository: OptionRepository,
) : BaseViewModel() {

    private val _image = MutableLiveData<Image?>()
    val image: LiveData<Image?> = _image

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
        const val EVENT_TOAST = 1001
    }

    fun setImage(image: Image) {
        _image.value = image
    }

    fun createOptionMenu(request: OptionCreateRequest, callback: () -> Unit) {
        optionMenuRepository.createOption(request)
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

                        it.toErrorResponseOrNull()?.let {
                            when(it.code) {
                                ErrorCode.ALREADY_OPTION_CREATED.code -> {
                                    viewEvent(Pair(EVENT_TOAST, "중복된 메뉴 코드입니다."))
                                }
                            }
                        }
                    },
                )
            }.addTo(compositeDisposable)
    }
}