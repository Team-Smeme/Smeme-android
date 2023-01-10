package com.sopt.smeme.bridge.agent.response

import kotlinx.serialization.Serializable

@Serializable
data class InitializingProfileRequest(
    val username: String, // Mandatory, not blank
    val bio: String, // Mandatory
)