package com.tenutz.storemngsim.data.datasource.api.dto.common

data class TokenRequest(
    val accessToken: String,
    val refreshToken: String,
)
