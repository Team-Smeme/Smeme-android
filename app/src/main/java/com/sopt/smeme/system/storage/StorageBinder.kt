package com.sopt.smeme.system.storage

import com.sopt.smeme.bridge.agent.InjectWay
import com.sopt.smeme.bridge.agent.Way
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StorageBinder {
    @Binds
    @Singleton
    @InjectWay(Way.MOCK)
    fun bindMockLocalStorage(
        volatileStorage: VolatileStorage
    ): LocalStorage

    @Binds
    @Singleton
    @InjectWay(Way.DEV)
    fun bindLocalStorage(
        localSharedPreference: LocalSharedPreference
    ): LocalStorage
}