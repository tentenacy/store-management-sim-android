package com.tenutz.storemngsim.usecase

import android.os.Parcelable
import com.tenutz.storemngsim.data.repository.sharing.SharedRepository

class PutSharedDataUseCase(private val sharedRepository: SharedRepository) {

    operator fun invoke(value: Parcelable) {
        sharedRepository.putParcelable(value)
    }
}