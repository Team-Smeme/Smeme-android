package com.sopt.smeme.bridge.agent.user

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.sopt.smeme.Own
import com.sopt.smeme.SignInType
import com.sopt.smeme.SmemeException
import com.sopt.smeme.Social
import com.sopt.smeme.bridge.controller.AuthConnection
import com.sopt.smeme.bridge.controller.request.AuthenticateRequest
import com.sopt.smeme.system.storage.AccessData
import com.sopt.smeme.system.storage.LocalStorage
import com.sopt.smeme.system.storage.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class SocialSignInAgent @Inject constructor(
    private val context: Context,
    private val localStorage: LocalStorage,
    private val authConnection: AuthConnection,
) : SignInAgent {
    var oAuthToken: OAuthToken? = null

    override fun call(
        type: SignInType.Type,
        onCompleted: (Boolean) -> Unit,
        onError: (RuntimeException) -> Unit,
    ) = when (type) {
        is Own.OWN -> {}
        is Social.NAVER -> {}
        is Social.KAKAO -> {

            try {
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    // kakao 로 부터 error 가 내려온 경우
                    if (error != null) {
                        Timber.e(error, "로그인 실패")
                        onError.invoke(SmemeException(500, "social server 인증에서 오류가 발생했습니다."))
                    }

                    // token 이 정상적으로 전달된 경우
                    else if (token != null) {
                        val oAuthToken = token

                        // local storage 에 kakao 정보를 담는다.
                        localStorage.save(
                            AccessData(
                                accessToken = oAuthToken.accessToken,
                                refreshToken = oAuthToken.refreshToken,
                                accessExpired = oAuthToken.accessTokenExpiresAt,
                                refreshExpired = oAuthToken.refreshTokenExpiresAt,
                                idToken = oAuthToken.idToken
                            )
                        )

                        // server 로 토큰 요청을 한다.
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val response = authConnection.login(
                                    AuthenticateRequest(social = "kakao"),
                                    "Bearer ${oAuthToken.accessToken}"
                                )

                                // server 로 부터 200 응답이 옴
                                if (response.isSuccessful()) {
                                    // save access info in local storage
                                    val data = response.data
                                    if (data == null) {
                                        throw SmemeException(500,
                                            "api server 로 부터 토큰 값이 넘어오지 않았습니다.")
                                    } else {
                                        // 이미 등록된 회원
                                        if (data.isRegistered) {
                                            localStorage.authorize()
                                        } else {

                                        }
                                        localStorage.save(
                                            UserData(
                                                accessToken = data.accessToken,
                                                refreshToken = data.refreshToken,
                                                id = null
                                            )
                                        )
                                        onCompleted.invoke(data.isRegistered)
                                    }
                                } else {
                                    onError.invoke(SmemeException(response.status,
                                        response.message))
                                }
                            } catch (e: HttpException) {
                                onError.invoke(e)
                            }
                        }
                    }
                }
             } catch(t: Throwable) {
                 t.printStackTrace()
            }
        }
    }
}