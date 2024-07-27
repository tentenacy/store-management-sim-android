package com.tenutz.storemngsim.ui.login.args

import android.os.Parcelable
import com.tenutz.storemngsim.utils.type.SocialType
import kotlinx.parcelize.Parcelize

@Parcelize
data class SocialProfileArgs(
    val accessToken: String,
    val socialType: SocialType,
    val name: String? = null,
    val email: String? = null,
    val profileImageUrl: String? = null,
): Parcelable
