package com.tenutz.storemngsim.ui.base

import com.tenutz.storemngsim.utils.type.SocialType

interface Loginable {

    fun socialLogin(accessToken: String, socialType: SocialType)
    fun logout()
}