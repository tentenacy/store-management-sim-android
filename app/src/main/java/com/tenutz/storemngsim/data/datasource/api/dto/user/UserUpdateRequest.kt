package com.tenutz.storemngsim.data.datasource.api.dto.user

data class UserUpdateRequest(
    val businessNumber: String,
    val ownerName: String,
    val phoneNumber: String,
    val storeName: String,
    val address: String,
)
