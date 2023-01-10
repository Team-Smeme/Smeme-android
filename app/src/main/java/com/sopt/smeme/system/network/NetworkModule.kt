package com.sopt.smeme.system.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.smeme.HomeAccess
import com.sopt.smeme.system.storage.LocalSharedPreference
import com.sopt.smeme.system.storage.UserData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @ExperimentalSerializationApi
    @Provides
    @Singleton
    @ConnectCluster(Cluster.ORIGIN, ConnectionType.AUTHORIZE)
    fun provideAuthConnectionRetrofit(
        @ConnectionWay(ConnectionType.AUTHORIZE) client: OkHttpClient, json: Json
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Cluster.ORIGIN.node())
        .client(client)
        .addConverterFactory(json.asConverterFactory(requireNotNull("application/json".toMediaTypeOrNull())))
        .build()

    /**
     * 인증 시에는 token setting 을 하지 않는다.
     */
    @Provides
    @Singleton
    @ConnectionWay(ConnectionType.AUTHORIZE)
    fun provideAuthOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
        }.build()

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    @ConnectCluster(Cluster.ORIGIN)
    fun provideNodeApiBaseRetrofit(
        @ConnectionWay(ConnectionType.ACCESS) client: OkHttpClient,
        json: Json
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Cluster.ORIGIN.node())
        .client(client)
        .addConverterFactory(json.asConverterFactory(requireNotNull("application/json".toMediaTypeOrNull())))
        .build()

    /**
     * api 요청 시에는 token setting 이 필요하다.
     */
    @Provides
    @Singleton
    @ConnectionWay(ConnectionType.ACCESS)
    fun provideAccessOkHttpClient(
        localStorage: LocalSharedPreference
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)

            val userToken = localStorage.get(HomeAccess)
            if (localStorage.isAuthenticated()) {
                if (userToken is UserData) {
                    addInterceptor(
                        RequestInterceptor(
                            userToken.accessToken ?: "",
                            userToken.refreshToken
                        )
                    )
                }
            }
        }.build()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        isLenient = true
        prettyPrint = true
        explicitNulls = false
    }
}