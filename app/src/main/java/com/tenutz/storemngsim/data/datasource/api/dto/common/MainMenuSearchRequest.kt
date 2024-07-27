package com.tenutz.storemngsim.data.datasource.api.dto.common

data class MainMenuSearchRequest(
    val mainCateCd: String = "2000",
    val middleCateCd: String? = "3000",
    val subCateCd: String?,
)
