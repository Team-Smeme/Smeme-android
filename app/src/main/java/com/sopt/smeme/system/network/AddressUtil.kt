package com.sopt.smeme.system.network

object AddressUtil {
    public fun domain(host: String, port: Int) = "${Protocol.using()}$host:$port"
}

object Protocol {
    const val HTTP = "http://"
    const val HTTPS = "https://"

    fun using() = HTTP
}