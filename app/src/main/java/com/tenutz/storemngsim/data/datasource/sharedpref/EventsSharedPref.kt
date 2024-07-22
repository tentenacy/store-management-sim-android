package com.tenutz.storemngsim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object EventsSharedPref: KotprefModel() {
    var existsNewNotification by booleanPref(false)
}