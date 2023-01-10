package com.sopt.smeme.system.network

object AddressUtil {
    public fun domainHttp(host: String, port: Int) = "${Protocol.using()}$host:$port"
    fun domainHttps(host: String) = "${Protocol.HTTPS}$host"
}

object Protocol {
    const val HTTP = "http://"
    const val HTTPS = "https://"

    fun using() = HTTP
}