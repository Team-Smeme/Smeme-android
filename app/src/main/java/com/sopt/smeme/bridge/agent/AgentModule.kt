package com.sopt.smeme.bridge.agent

import android.content.Context
import com.sopt.smeme.bridge.agent.user.MockSignInAgent
import com.sopt.smeme.bridge.agent.user.SignInAgent
import com.sopt.smeme.bridge.agent.user.SocialSignInAgent
import com.sopt.smeme.bridge.controller.AuthConnection
import com.sopt.smeme.system.storage.LocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

object AgentModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    object User {
        @Provides
        @ViewModelScoped
        @InjectWay(Way.MOCK)
        fun provideMockSignInAgent(
            @ApplicationContext context: Context,
            @InjectWay(Way.MOCK) localStorage: LocalStorage,
        ): SignInAgent = MockSignInAgent(context, localStorage)

        @Provides
        @ViewModelScoped
        @InjectWay(Way.DEV)
        fun provideSocialSignInAgent(
            @ApplicationContext context: Context,
            @InjectWay(Way.DEV) localStorage: LocalStorage,
            authConnection: AuthConnection
        ): SignInAgent = SocialSignInAgent(context, localStorage, authConnection)
    }
}