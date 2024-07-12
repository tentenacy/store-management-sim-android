package com.tenutz.storemngsim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel
import com.tenutz.storemngsim.utils.type.SocialType

object OAuthToken: KotprefModel() {
    var accessToken by stringPref()
    var refreshToken by nullableStringPref()
    var socialType by stringPref()

    fun save(
        accessToken: String,
        refreshToken: String?,
        socialType: String,
    ) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        this.socialType = socialType
    }
}