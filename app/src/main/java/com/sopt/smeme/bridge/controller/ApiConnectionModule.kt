package com.sopt.smeme.bridge.controller

import com.sopt.smeme.system.network.Cluster
import com.sopt.smeme.system.network.ConnectCluster
import com.sopt.smeme.system.network.ConnectionType
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
        fun provideLogin(
            @ConnectCluster(
                Cluster.ORIGIN,
                ConnectionType.AUTHORIZE
            ) retrofit: Retrofit
        ): AuthConnection =
            retrofit.create(AuthConnection::class.java)

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
        fun provideMyDiaryConnection(@ConnectCluster(Cluster.ORIGIN) retrofit: Retrofit): MyDiaryConnection =
            retrofit.create(MyDiaryConnection::class.java)

        @Provides
        @ViewModelScoped
        fun provideDiaryWriteConnection(@ConnectCluster(Cluster.ORIGIN) retrofit: Retrofit): DiaryWriteConnection =
            retrofit.create(DiaryWriteConnection::class.java)
        @Provides
        @ViewModelScoped
        fun providePapago(@ConnectCluster(Cluster.PAPAGO) retrofit: Retrofit): PapagoAPI =
            retrofit.create(PapagoAPI::class.java)
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    object ODir {
        // open diary list
        @Provides
        @ViewModelScoped
        fun provideOdirListConnection(@ConnectCluster(Cluster.ORIGIN) retrofit: Retrofit) : OdirListConnection =
            retrofit.create(OdirListConnection::class.java)

        // open diary detail
        @Provides
        @ViewModelScoped
        fun provideOdirDetailConnection(@ConnectCluster(Cluster.ORIGIN) retrofit: Retrofit) : OdirDetailConnection =
            retrofit.create(OdirDetailConnection::class.java)
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    object Else {
        @Provides
        @ViewModelScoped
        fun provideRandomTopic(@ConnectCluster(Cluster.ORIGIN) retrofit: Retrofit): TopicConnection =
            retrofit.create(TopicConnection::class.java)
    }
}