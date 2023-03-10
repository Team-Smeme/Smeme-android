package com.sopt.smeme.bridge.agent

import javax.inject.Qualifier

@Qualifier
annotation class InjectWay(val way: Way = Way.DEV)

enum class Way {
    MOCK,
    LOCAL,
    DEV,
    PROD
}
