package com.tenutz.storemngsim.data.datasource.api.dto.common

data class TokenResponse(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long,
) {
    fun isEmpty() = grantType.isBlank() || accessToken.isBlank() || refreshToken.isBlank() || accessTokenExpiresIn == 0L
    fun isNotEmpty() = !isEmpty()
}
