package com.tenutz.storemngsim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object OAuthToken: KotprefModel() {
    var accessToken by stringPref()
    var refreshToken by stringPref()
}