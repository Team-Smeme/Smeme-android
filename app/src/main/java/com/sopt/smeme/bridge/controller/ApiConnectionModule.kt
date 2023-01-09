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

    @Module
    @InstallIn(ViewModelComponent::class)
    object User {
        @Provides
        @ViewModelScoped
        fun provideProfileInitializer(@ConnectCluster(Cluster.ORIGIN) retrofit: Retrofit): ProfileConnection =
            retrofit.create(ProfileConnection::class.java)

    }

    @Module
    @InstallIn(ViewModelComponent::class)
    object MDir {
        @Provides
        @ViewModelScoped
        fun provideMyDiaryConnection(@ConnectCluster(Cluster.ORIGIN) retrofit: Retrofit) : MyDiaryConnection =
            retrofit.create(MyDiaryConnection::class.java)
    }

    object ODir {

    }
}