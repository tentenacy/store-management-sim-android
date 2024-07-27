package com.tenutz.storemngsim.utils.type

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class SocialType: Parcelable {
    object KAKAO : SocialType()
    object GOOGLE : SocialType()
    object NAVER : SocialType()
    object FACEBOOK : SocialType()

    val name: String get() = when(this) {
        is KAKAO -> "kakao"
        is GOOGLE -> "google"
        is NAVER -> "naver"
        is FACEBOOK -> "facebook"
    }

    companion object {

        fun of(name: String): SocialType? {
            return when(name) {
                "kakao" -> KAKAO
                "google" -> GOOGLE
                "naver" -> NAVER
                "facebook" -> FACEBOOK
                else -> null
            }
        }
    }
}