package com.sopt.smeme.bridge.agent.response

import kotlinx.serialization.Serializable

@Serializable
data class InitializingProfileRequest(
    val name: String, // Mandatory, not blank
    val bio: String, // Mandatory
)