package com.tenutz.storemngsim.data.datasource.api.dto.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class UserDetailsResponse(
    val seq: String,
    val siteCode: String,
    val storeCode: String,
    val userId: String,
    @SerializedName("username") val ownerName: String?,
    val provider: String = "",
    val businessNumber: String = "",
    val phoneNumber: String,
    val storeName: String?,
    var address: String?,
    val registeredAt: Date?,
): Parcelable
