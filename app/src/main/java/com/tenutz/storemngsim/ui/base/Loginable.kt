package com.tenutz.storemngsim.ui.base

import com.tenutz.storemngsim.ui.login.args.SocialProfileArgs

interface Loginable {

    fun socialLogin(args: SocialProfileArgs)
    fun logout()
}