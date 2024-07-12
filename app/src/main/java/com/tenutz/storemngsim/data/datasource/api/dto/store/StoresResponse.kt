package com.tenutz.storemngsim.data.datasource.api.dto.store

data class StoresResponse(
    val stores: List<Store>,
) {
    data class Store(
        val storeCode: String?,
        val storeName: String?,
    )
}
