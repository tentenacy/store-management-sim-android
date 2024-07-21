package com.tenutz.storemngsim.utils.type

sealed class SocialType {
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