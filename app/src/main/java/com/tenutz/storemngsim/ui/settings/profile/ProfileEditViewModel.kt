package com.tenutz.storemngsim.ui.settings.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.user.UserDetailsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.user.UserUpdateRequest
import com.tenutz.storemngsim.data.repository.user.UserRepository
import com.tenutz.storemngsim.di.qualifier.NavigationGraphReference
import com.tenutz.storemngsim.di.qualifier.NavigationGraphs
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.settings.profile.args.ProfileArgs
import com.tenutz.storemngsim.usecase.GetMiddleCategoryUseCase
import com.tenutz.storemngsim.usecase.PutSharedDataUseCase
import com.tenutz.storemngsim.utils.ext.orEmpty
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_TOAST = 1000
    }

    val ownerName = MutableLiveData<String>("")

    val phoneNumber = MutableLiveData<String>("")

    val storeName = MutableLiveData<String>("")

    private val _address = MutableLiveData<String>("")
    val address: LiveData<String> = _address

    val addressDetails = MutableLiveData<String>("")

    fun setPhoneNumber(phoneNumber: String) {
        this.phoneNumber.value = phoneNumber
    }

    fun setAddress(address: String, addressDetails: String) {
        this._address.value = address
        this.addressDetails.value = addressDetails
    }

    fun mapToLiveData(args: ProfileArgs) {
        ownerName.value = args.ownerName
        phoneNumber.value = args.phoneNumber
        storeName.value = args.storeName
        args.address?.split("|")?.let {
            _address.value = it.getOrNull(0) ?: ""
            addressDetails.value = it.getOrNull(1) ?: ""
        }
    }

    fun updateProfile(args: ProfileArgs, callback: () -> Unit) {
        Validator.validate(
            onValidation = {
                Validator.validateRepresentative(ownerName.value.orEmpty)
                Validator.validatePhoneNumber(phoneNumber.value.orEmpty, true)
                Validator.validateStoreName(storeName.value.orEmpty, true)
                Validator.validateAddress(address.value.orEmpty, addressDetails.value.orEmpty)
            },
            onSuccess = {
                userRepository.update(
                    args.seq,
                    UserUpdateRequest(
                        args.businessNumber,
                        ownerName.value.orEmpty,
                        phoneNumber.value.orEmpty,
                        storeName.value.orEmpty,
                        "${address.value.orEmpty}|${addressDetails.value.orEmpty}",
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        result.fold(
                            onSuccess = {
                                callback()
                            },
                            onFailure = {
                                Logger.e("${it.message}")
                            },
                        )
                    }.addTo(compositeDisposable)
            },
            onFailure = {
                viewEvent(Pair(EVENT_TOAST, it.errorCode.message))
            },
        )
    }

}