package com.tenutz.storemngsim.data.datasource.api.dto.user

data class SocialSignupRequest(
    val accessToken: String,
    val businessNumber: String,
    val phoneNumber: String,
    val managerName: String,
    val storeName: String,
    val address: String,
)
