package com.tenutz.storemngsim.usecase

import com.tenutz.storemngsim.data.repository.store.StoreRepository

class GetStoreMainUseCase(private val storeRepository: StoreRepository) {
    operator fun invoke() = storeRepository.storeMain()
}