package com.sopt.smeme.bridge.agent.user

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import com.sopt.smeme.Own
import com.sopt.smeme.SignInType
import com.sopt.smeme.Social
import com.sopt.smeme.system.storage.AccessData
import com.sopt.smeme.system.storage.LocalStorage
import timber.log.Timber
import javax.inject.Inject


class SocialSignInAgent @Inject constructor(
    val context: Context,
    private val localStorage: LocalStorage
) : SignInAgent {

    override fun call(type: SignInType.Type) {
        when (type) {
            is Own.OWN -> {}
            is Social.NAVER -> {}
            is Social.KAKAO -> {
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->

                    // TODO : control error detail
                    if (error != null) {
                        Timber.e(error, "로그인 실패")
                    } else if (token != null) {
                        localStorage.save(
                            AccessData(
                                accessToken = token.accessToken,
                                refreshToken = token.refreshToken,
                                accessExpired = token.accessTokenExpiresAt,
                                refreshExpired = token.refreshTokenExpiresAt,
                                idToken = token.idToken
                            )
                        )
                    }
                }
            }
        }
    }
}
