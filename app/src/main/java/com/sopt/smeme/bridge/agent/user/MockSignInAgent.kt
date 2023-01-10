package com.sopt.smeme.bridge.agent.user

import android.content.Context
import com.sopt.smeme.Own
import com.sopt.smeme.SignInType
import com.sopt.smeme.Social
import com.sopt.smeme.system.storage.AccessData
import com.sopt.smeme.system.storage.LocalStorage
import java.time.Instant
import java.util.*
import javax.inject.Inject

class MockSignInAgent @Inject constructor(
    context: Context,
    private val localStorage: LocalStorage
) : SignInAgent {

    override fun call(
        type: SignInType.Type,
        onCompleted: (Boolean) -> Unit,
        onError: (RuntimeException) -> Unit
    ) {
        when (type) {
            is Own.OWN -> {}
            is Social.NAVER -> {}
            is Social.KAKAO -> {
                localStorage.save(
                    AccessData(
                        accessToken = "mock_access_token",
                        accessExpired = Date(Instant.now().plusSeconds(60 * 60).toEpochMilli()),
                        refreshToken = "mock_refresh_token",
                        refreshExpired = Date(Instant.now().plusSeconds(60 * 60).toEpochMilli()),
                        idToken = null
                    )
                )
                onCompleted.invoke(true)
            }
        }
    }
}