package com.sopt.smeme.system.network

import com.sopt.smeme.BuildConfig.*
import com.sopt.smeme.system.network.AddressUtil.domain
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
        domain(ORIGIN_API_BASE_HOST, ORIGIN_API_BASE_PORT),
        mapOf(Pair(ORIGIN_API_BASE_NAME, domain(ORIGIN_API_BASE_HOST, ORIGIN_API_BASE_PORT)))
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