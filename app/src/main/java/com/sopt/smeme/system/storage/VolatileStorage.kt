package com.sopt.smeme.system.storage

import com.sopt.smeme.HomeAccess
import com.sopt.smeme.KakaoLoginAccess
import com.sopt.smeme.UserAccessType
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VolatileStorage @Inject constructor() : LocalStorage {
    private val memory: ConcurrentHashMap<String, Any> = ConcurrentHashMap()

    override fun save(data: LocalStorage.Data) {
        when (data) {
            // user data //
            is UserData -> {
                memory.put(LocalStorage.ID, data.id ?: -1)
                memory.put(LocalStorage.ACCESS_TOKEN, data.accessToken as String)
                memory.put(LocalStorage.REFRESH_TOKEN, data.refreshToken as String)
                memory.put(LocalStorage.LOGIN_CHECKED, true)
            }

            // oauth access data //
            is AccessData -> {
                memory.put(LocalStorage.KAKAO_ID_TOKEN, data.idToken ?: "")
                memory.put(LocalStorage.KAKAO_ACCESS_TOKEN, data.accessToken)
                memory.put(LocalStorage.KAKAO_REFRESH_TOKEN, data.refreshToken)
                memory.put(
                    LocalStorage.KAKAO_ACCESS_EXPIRED,
                    data.accessExpired.toInstant().toEpochMilli()
                )
                memory.put(
                    LocalStorage.KAKAO_REFRESH_EXPIRED,
                    data.refreshExpired.toInstant().toEpochMilli()
                )
                memory.put(LocalStorage.LOGIN_CHECKED, true)
            }

            is LocalStorage.EMPTY -> {
                throw Exception("wrong access for local data")
            }
        }
    }

    override fun get(accessType: UserAccessType): LocalStorage.Data {
        with(memory) {
            if (!(memory.get(LocalStorage.LOGIN_CHECKED) as Boolean)) return LocalStorage.EMPTY

            when (accessType) {
                is KakaoLoginAccess -> return AccessData(
                    accessToken = memory.get(LocalStorage.KAKAO_ACCESS_TOKEN) as String,
                    refreshToken = memory.get(LocalStorage.KAKAO_REFRESH_TOKEN) as String,
                    accessExpired = Date(memory.get(LocalStorage.KAKAO_ACCESS_EXPIRED) as Long),
                    refreshExpired = Date(memory.get(LocalStorage.KAKAO_REFRESH_EXPIRED) as Long),
                    idToken = memory.get(LocalStorage.KAKAO_ID_TOKEN) as String
                )

                is HomeAccess -> return UserData(
                    id = memory.get(LocalStorage.ID) as Long,
                    accessToken = memory.get(LocalStorage.ACCESS_TOKEN) as String,
                    refreshToken = memory.get(LocalStorage.REFRESH_TOKEN) as String
                )
            }
        }
    }

    override fun isAuthenticated() = memory.get(LocalStorage.LOGIN_CHECKED) as Boolean
}