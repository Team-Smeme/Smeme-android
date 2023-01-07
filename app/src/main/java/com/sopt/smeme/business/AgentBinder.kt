package com.sopt.smeme.business

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface AgentBinder {
    @Binds
    @ViewModelScoped
    fun bindAgent(apiHealthCheckAgent: ApiHealthCheckAgent): HealthCheckAgent
}