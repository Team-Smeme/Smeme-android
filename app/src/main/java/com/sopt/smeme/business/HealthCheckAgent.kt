package com.sopt.smeme.business

import com.sopt.smeme.bridge.controller.HealthCheckConnection
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class HealthCheckAgent @Inject constructor(
    private val healthCheckConnection: HealthCheckConnection
) {
    suspend fun call() = healthCheckConnection.run()
}