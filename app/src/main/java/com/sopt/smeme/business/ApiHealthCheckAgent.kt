package com.sopt.smeme.business

import com.sopt.smeme.bridge.controller.HealthCheckConnection
import javax.inject.Inject

class ApiHealthCheckAgent @Inject constructor(
    private val healthCheckConnection: HealthCheckConnection
) : HealthCheckAgent {
    override suspend fun call() = healthCheckConnection.run()
}