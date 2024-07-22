package com.tenutz.storemngsim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object User: KotprefModel() {
    var fcmToken by stringPref()
}