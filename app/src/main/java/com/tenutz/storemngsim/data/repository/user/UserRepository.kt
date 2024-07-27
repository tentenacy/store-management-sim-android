package com.tenutz.storemngsim.data.repository.user

import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenResponse
import com.tenutz.storemngsim.data.datasource.api.dto.user.SocialSignupRequest
import com.tenutz.storemngsim.data.datasource.api.dto.user.UserDetailsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.user.UserUpdateRequest
import com.tenutz.storemngsim.utils.type.SocialType
import io.reactivex.rxjava3.core.Single

interface UserRepository {

    fun socialSignup(
        socialType: String,
        request: SocialSignupRequest
    ): Single<Unit>

    fun socialLogin(
        socialType: SocialType,
    ): Single<TokenResponse>

    fun reissue(request: TokenRequest): Single<TokenResponse>

    fun userDetails(): Single<Result<UserDetailsResponse>>

    fun update(userSeq: String, request: UserUpdateRequest): Single<Result<Unit>>
}