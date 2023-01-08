package com.sopt.smeme.bridge.agent.user

import com.sopt.smeme.bridge.controller.ProfileConnection
import com.sopt.smeme.bridge.controller.response.SimpleResponse
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MockProfileEditAgent @Inject constructor(
    profileConnection: ProfileConnection
) : ProfileAgent {
    override suspend fun create(nickname: String, introducing: String)
    = SimpleResponse(200, true, "success")
}