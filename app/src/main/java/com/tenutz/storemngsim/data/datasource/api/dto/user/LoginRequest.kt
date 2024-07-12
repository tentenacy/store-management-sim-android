package com.tenutz.storemngsim.data.datasource.api.dto.user

data class LoginRequest(
    val id: String,
    val password: String,
    val provider: String? = null,
)
