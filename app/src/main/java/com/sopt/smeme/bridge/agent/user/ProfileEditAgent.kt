package com.sopt.smeme.bridge.agent.user

import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.agent.response.InitializingProfileRequest
import com.sopt.smeme.bridge.controller.ProfileConnection
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException
import javax.inject.Inject

@ViewModelScoped
class ProfileEditAgent @Inject constructor(
    private val profileConnection: ProfileConnection
) : ProfileAgent {
    override suspend fun create(
        nickname: String,
        introducing: String,
        token: String?,
        onCompleted: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            val response = profileConnection.initialize(
                InitializingProfileRequest(nickname, introducing),
                "Bearer ${token}"
            )

            if (response.isSuccessful()) {
                onCompleted.invoke()
            }

            throw SmemeException(response.status, response.message ?: "관리자에 문의하세요.")
        } catch (s: SmemeException) {
            onError.invoke(s)
        } catch (e: HttpException) {
            onError.invoke(e)
        }
    }
}