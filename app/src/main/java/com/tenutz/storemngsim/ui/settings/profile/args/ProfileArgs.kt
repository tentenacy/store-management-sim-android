package com.tenutz.storemngsim.ui.settings.profile.args

import android.os.Parcelable
import com.tenutz.storemngsim.data.datasource.api.dto.user.UserDetailsResponse
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ProfileArgs(
    val seq: String,
    val siteCode: String,
    val storeCode: String,
    val userId: String,
    val ownerName: String,
    val provider: String,
    val businessNumber: String,
    val phoneNumber: String,
    val storeName: String,
    var address: String?,
    val registeredAt: Date?,
): Parcelable {
    companion object {
        fun of(dto: UserDetailsResponse) = ProfileArgs(
            seq = dto.seq,
            siteCode = dto.siteCode,
            storeCode = dto.storeCode,
            userId = dto.userId,
            ownerName = dto.ownerName ?: "",
            provider = dto.provider,
            businessNumber = dto.businessNumber,
            phoneNumber = dto.phoneNumber,
            storeName = dto.storeName ?: "",
            address = dto.address,
            registeredAt = dto.registeredAt,
        )
    }
}
