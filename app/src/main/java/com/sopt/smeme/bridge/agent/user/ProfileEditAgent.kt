package com.sopt.smeme.bridge.agent.user

import com.sopt.smeme.bridge.agent.response.InitializingProfileRequest
import com.sopt.smeme.bridge.controller.ProfileConnection
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ProfileEditAgent @Inject constructor(
    private val profileConnection: ProfileConnection
) : ProfileAgent {
    override suspend fun create(nickname: String, introducing: String) =
        profileConnection.initialize(InitializingProfileRequest(nickname, introducing))
}