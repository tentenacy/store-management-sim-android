package com.tenutz.storemngsim.data.datasource.api.dto.user

import java.util.*

data class UserDetailsResponse(
    val seq: String?,
    val siteCode: String?,
    val storeCode: String?,
    val username: String?,
    val businessNumber: String?,
    val contact: String?,
    val userId: String?,
    val provider: String?,
    val registeredAt: Date?,
    val receiveYn: String?,
)
