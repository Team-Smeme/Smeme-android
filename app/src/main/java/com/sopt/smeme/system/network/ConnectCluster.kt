package com.sopt.smeme.system.network

import com.sopt.smeme.BuildConfig.*
import com.sopt.smeme.system.network.AddressUtil.domainHttp
import com.sopt.smeme.system.network.AddressUtil.domainHttps
import javax.inject.Qualifier

@Qualifier
annotation class ConnectCluster(
    val cluster: Cluster = Cluster.ORIGIN,
    val connectionType: ConnectionType = ConnectionType.ACCESS
)

enum class Cluster(
    val default: String,
    val nodes: Map<String, String>
) {
    ORIGIN(
        domainHttp(ORIGIN_API_BASE_HOST, ORIGIN_API_BASE_PORT),
        mapOf(Pair(ORIGIN_API_BASE_NAME, domainHttp(ORIGIN_API_BASE_HOST, ORIGIN_API_BASE_PORT)))
    ),
    PAPAGO(
        domainHttps("openapi.naver.com/"),
        mapOf(Pair("PAPAGO", domainHttps("openapi.naver.com/")))
    )
    ;

    fun node() = default
    fun node(target: String) = nodes.getOrDefault(target, default)
}

@Qualifier
annotation class ConnectionWay(
    val connectionType: ConnectionType = ConnectionType.ACCESS
)

enum class ConnectionType {
    ACCESS,
    AUTHORIZE
}