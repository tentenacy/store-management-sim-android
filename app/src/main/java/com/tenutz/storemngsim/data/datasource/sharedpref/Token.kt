package com.tenutz.storemngsim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object Token: KotprefModel() {
    var grantType by stringPref()
    var accessToken by stringPref()
    var refreshToken by stringPref()
    var accessTokenExpireIn by longPref()
}