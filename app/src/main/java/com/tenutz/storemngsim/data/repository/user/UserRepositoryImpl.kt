package com.tenutz.storemngsim.data.repository.user

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenResponse
import com.tenutz.storemngsim.data.datasource.api.dto.user.SocialLoginRequest
import com.tenutz.storemngsim.data.datasource.api.dto.user.SocialSignupRequest
import com.tenutz.storemngsim.data.datasource.api.dto.user.UserDetailsResponse
import com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken
import com.tenutz.storemngsim.utils.type.SocialType
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
) : UserRepository {
    override fun socialSignup(socialType: String, request: SocialSignupRequest): Single<Unit> =
        SMSApi.socialSignup(
            socialType,
            request,
        )

    override fun socialLogin(
        socialType: SocialType,
    ): Single<TokenResponse> =
        SMSApi.socialLogin(
            socialType.name,
            SocialLoginRequest(OAuthToken.accessToken),
        )

    override fun reissue(request: TokenRequest): Single<TokenResponse> =
        SMSApi.reissue(request)

    override fun userDetails(): Single<UserDetailsResponse> =
        SMSApi.userDetails()
}