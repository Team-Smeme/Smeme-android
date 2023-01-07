package com.sopt.smeme.bridge.controller

import com.sopt.smeme.system.network.Cluster
import com.sopt.smeme.system.network.ConnectCluster
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

object ApiConnectionModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    object Health {
        @Provides
        @ViewModelScoped
        fun provideHealthChecker(@ConnectCluster(Cluster.ORIGIN) retrofit: Retrofit): HealthCheckConnection =
            retrofit.create(HealthCheckConnection::class.java)

    }

    object User {

    }

    object MDir {

    }

    object ODir {

    }
}