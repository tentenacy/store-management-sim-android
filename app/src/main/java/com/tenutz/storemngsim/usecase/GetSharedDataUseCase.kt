package com.tenutz.storemngsim.usecase

import android.os.Parcelable
import com.tenutz.storemngsim.data.repository.sharing.SharedRepository
import kotlin.reflect.KClass

class GetSharedDataUseCase(private val sharedRepository: SharedRepository) {

    operator fun <T: Parcelable>invoke(key: KClass<T>): T? = sharedRepository.getParcelable(key)
}