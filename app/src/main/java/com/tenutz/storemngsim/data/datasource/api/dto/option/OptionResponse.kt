package com.tenutz.storemngsim.data.datasource.api.dto.option

data class OptionResponse(
    val storeCode: String,
    val optionCode: String,
    val optionName: String?,
    val price: Int,
    val use: Boolean?,
    val imageName: String?,
    val imageUrl: String?,
)
