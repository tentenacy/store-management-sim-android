package com.tenutz.storemngsim.data.datasource.api.dto.user

data class SignupRequest(
    val businessNumber: String,
    val phoneNumber: String,
    val type1Agreement: Boolean,
    val type2Agreement: Boolean,
)
